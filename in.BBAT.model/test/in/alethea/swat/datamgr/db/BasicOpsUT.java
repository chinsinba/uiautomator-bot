package in.alethea.swat.datamgr.db;

import static org.junit.Assert.assertEquals;
import in.alethea.swat.datamgr.db.model.TestCampaignData;
import in.alethea.swat.datamgr.db.model.TestCampaignRootData;
import in.alethea.swat.datamgr.db.model.TestCaseData;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Ignore;
import org.junit.Test;

public class BasicOpsUT {

	private static String persistenceUnitName = "TestCaseData";

	private static String dbName = "testcaseDB01";
	
	private static String defaultRootName = "DEFAULT";

	private final boolean CREATE_DB = true;

	private final boolean NO_CREATE_DB = false;

	private final boolean NETWORK_DB = true;

	//private final boolean EMBEDDED_DB = false;

	private TestCampaignRootData defaultRoot;


	private EntityManagerHelper createEntityManagerHelper() {
		EntityManagerHelper emh=null;
		try {
			emh = EntityManagerHelper.createEntityManager(
					persistenceUnitName, NO_CREATE_DB, NETWORK_DB, dbName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emh;
	}
	
	private void createTables() {
		// Create default tables.
		EntityManagerHelper emh=null;
		try {
			emh = EntityManagerHelper.createEntityManager(
					persistenceUnitName, CREATE_DB, NETWORK_DB, dbName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		emh.closeEntityManager();
	}


	private void storeOneTestData(EntityManagerHelper emh, String name,
			String suite, String pack, TestCampaignData c) {
		TestCaseData t0 = new TestCaseData();
		t0.setName(name);
		t0.setSuiteName(suite);
		t0.setPackageName(pack);
		t0.setCampaign(c);
		t0.setResultDir(name);

		c.getTestcases().add(t0);

		// Persist the object
		// emh.beginTransaction();
		emh.persist(t0);
		// emh.commitTransaction();
	}

	private TestCampaignData storeOneCampaign(EntityManagerHelper emh,
			String name, int idx, TestCampaignRootData root) {
		TestCampaignData c = new TestCampaignData();
		c.setName(String.format("%s%02d", name, idx));
		c.setCampaignRoot(root);

		root.getCampaigns().add(c);

		// Persist the object
		emh.persist(c);

		System.out.println(c.toString());

		return c;
	}

	private void juVerifyTestCampaign(EntityManagerHelper emh) {
		EntityManager em = emh.getEm();
		
		em.clear();

		List<TestCampaignRootData> rootList = DbQuery.fetchAllTestCampaigns();
		//em.createQuery("SELECT r FROM TestCampaignRootData r").getResultList();
		assertEquals("Number of roots", 1, rootList.size());
		
		TestCampaignRootData root = DbQuery.fetchTestCampaignRootData(defaultRootName);
		System.out.println(root.toString());
		
		assertEquals("Number of campaigns", 2, root.getCampaigns().size());
		TestCampaignData ce0 = root.getCampaigns().get(0);
		TestCampaignData ce1 = root.getCampaigns().get(1);
		System.out.println("    " + ce0.toString());
		System.out.println("    " + ce1.toString());
		
		String expectedSuite = "suite";
		String expectedPack = "pack";

		assertEquals("Test cases in campaign1", 3, ce0.getTestcases().size());
		assertEquals("Test cases in campaign2", 3, ce1.getTestcases().size());

		// Verify test cases
		for (TestCaseData t : ce0.getTestcases()) {
			System.out.println("        " + t.toString());
			assertEquals("Suite name", expectedSuite, t.getSuiteName());
			assertEquals("Package name", expectedPack, t.getPackageName());
		}

		for (TestCaseData t : ce1.getTestcases()) {
			System.out.println("        " + t.toString());
			assertEquals("Suite name", expectedSuite, t.getSuiteName());
			assertEquals("Package name", expectedPack, t.getPackageName());
		}
	}

	private void juCreateTestCampaign(EntityManagerHelper emh) {
		emh.beginTransaction();

		defaultRoot = new TestCampaignRootData();
		defaultRoot.setName(defaultRootName);

		emh.persist(defaultRoot);

		// Add campaigns
		TestCampaignData c1 = storeOneCampaign(emh, "campaign", 1, defaultRoot);
		TestCampaignData c2 = storeOneCampaign(emh, "campaign", 2, defaultRoot);

		// Add 3 tests to first campaign
		storeOneTestData(emh, "test1", "suite", "pack", c1);
		storeOneTestData(emh, "test2", "suite", "pack", c1);
		storeOneTestData(emh, "test3", "suite", "pack", c1);

		// Add 3 tests to second campaign
		storeOneTestData(emh, "test1", "suite", "pack", c2);
		storeOneTestData(emh, "test2", "suite", "pack", c2);
		storeOneTestData(emh, "test3", "suite", "pack", c2);

		emh.commitTransaction();
	}

	@Test
	public void juCreateTestCampaign() {
		createTables();
		
		EntityManagerHelper emh = createEntityManagerHelper(); 

		juCreateTestCampaign(emh);

		emh.closeEntityManager();

		// Create a fresh entity manager, without this, when we query campaign
		// from database (using find below), it returns cached copy and it does
		// not contain the test cases we have added.
		// Another way could be to call em.refresh(campaign). It results in
		// additional access to database.
		// Another way could be to add test cases in the campaign here to make
		// sure the copy in database and cached copy are in sync.
		emh = createEntityManagerHelper();

		juVerifyTestCampaign(emh);

		emh.closeEntityManager();
	}
	
	@Test
	public void juFetchCampaign() {
		EntityManagerHelper emh = createEntityManagerHelper();

		TestCampaignData c1 = DbQuery.fetchTestCampaign(defaultRootName, "campaign01");
		System.out.println(c1.toString());
		assertEquals("name", "campaign01", c1.getName());
		
		
		TestCampaignData c2 = DbQuery.fetchTestCampaign(defaultRootName, "campaign02");
		System.out.println(c2.toString());
		assertEquals("name", "campaign02", c2.getName());

		
		TestCaseData t1 = DbQuery.fetchTestCase(c1, "test1");
		System.out.println(t1.toString());
		
		TestCaseData t2 = DbQuery.fetchTestCase(c2, "test3");
		System.out.println(t2.toString());
		
		emh.closeEntityManager();
	}

	
	@Test
	public void juAddCampaign() {
		EntityManagerHelper emh = createEntityManagerHelper();

		emh.beginTransaction();
		
		TestCampaignRootData root = DbQuery.fetchTestCampaignRootData(defaultRootName);
		System.out.println(root.toString());

		TestCampaignData c = new TestCampaignData();
		c.setName("campaign3");
		c.setCampaignRoot(root);
		root.getCampaigns().add(c);

		emh.persist(root);

		emh.persist(c);
		
		EntityManager em = emh.getEm();
		
		em.flush();
		//em.refresh(root);

		emh.commitTransaction();
		
		System.out.println(root.toString());
		
		emh.closeEntityManager();
		
		emh = createEntityManagerHelper();
		root = DbQuery.fetchTestCampaignRootData(defaultRootName);
		System.out.println(root.toString());
		
		for(TestCampaignData c1 : root.getCampaigns()) {
			System.out.println("    " + c1.toString());
		}
				
		emh.closeEntityManager();
	}
	
	
	@Ignore
	@Test
	public void juDeleteTestCampaignEmbedded() {
		juCreateTestCampaign();
		EntityManagerHelper emh = createEntityManagerHelper();
		emh.closeEntityManager();
	}

}
