#! /bin/sh

# Script to install ****

resolvpath() {
    # expand ~ if given
    if [ "`echo $1 | cut -f1 -d/`" = "~" ]; then
        suffix=`echo $1 | cut -f2- -d/`
        path=$HOME/$suffix
    else
        path=$1
    fi

    # resolve symlinks
    while [ -h $path ]; do
        # 1) cd to directory of the symlink
        # 2) cd to the directory of where the symlink points
        # 3) get the pwd
        # 4) append the basename
        dir=$(dirname -- "$path")
        sym=$(readlink $path)
        path=$(cd $dir && cd $(dirname -- "$sym") && pwd)/$(basename -- "$sym")
    done

    if [ -r $path ]; then
        echo $path
    fi
}

# Become self-aware. Where am I?
bbatdir=$(dirname $0)
bbatpath=$bbatdir/bbat
if [ ! -x $bbatpath ]; then
    bbatpath=$bbatdir/linux32/bbat
    if [ -x $bbatpath ]; then
        bbatdir=$bbatdir/linux32
    fi
fi
if [ ! -x $bbatpath ]; then
    bbatpath=$bbatdir/linux64/bbat
    if [ -x $bbatpath ]; then
        bbatdir=$bbatdir/linux64
    fi
fi
while [ ! -x $bbatpath ]; do
    # oops! could not find bbat
    echo -n "Error locating bbat directory. Please specify the directory where bbat is present: "
    read dir
    bbatdir=$(resolvpath $dir)
    bbatpath=$bbatdir/bbat
done

installdir=$HOME/.bbat-dist
inifile=$installdir/bbat.ini

# Show the EULA and get user's ascent
echo "Here is the EULA for ****:"
more EULA.txt
echo
echo -n "Do you accept this agreement? (Enter y to accept the EULA, n to reject): "
read resp
if [ "$resp" != "y" ]; then
    echo "EULA not accepted. Aborting **** install"
    exit 1
fi

# TODO :
# copy bbat directory to
echo "Installing bbat in ~/.bbat-dist and creating a symlink from /usr/bin for bbat"
echo "This may require you to enter your password for sudo command"
if [ -d $installdir ]; then
    rm -r $installdir
fi

mkdir -p $installdir
cp -r $bbatdir/* $installdir
if [ -h /usr/bin/bbat ]; then
    # There is already a bbat symlink in /usr/bin. Check if it's
    # correct
    if [ ! "$(readlink /usr/bin/bbat)" = "$installdir/bbat" ]; then
        # It's not the right link. Move it and create a new symlink
        sudo mv /usr/bin/bbat /usr/bin/bbat.old
        sudo ln -s $installdir/bbat /usr/bin/bbat
    fi
elif [ -r /usr/bin/bbat ]; then
    # There is already a **** in /usr/bin, and it's not a symlink
    # Move it and create a new symlink
    sudo mv /usr/bin/bbat /usr/bin/bbat.old
    sudo ln -s $installdir/bbat /usr/bin/bbat
else
    sudo ln -s $installdir/bbat /usr/bin/bbat
fi

if [ -h /usr/bin/bbat-nowin ]; then
    if [ ! "$(readlink /usr/bin/bbat-nowin)" = "$installdir/bbat-nowin" ]; then
        sudo mv /usr/bin/bbat-nowin /usr/bin/bbat-nowin.old
        sudo ln -s $installdir/bbat-nowin /usr/bin/bbat-nowin
    fi
elif [ -r /usr/bin/bbat-nowin ]; then
    sudo mv /usr/bin/bbat /usr/bin/bbat-nowin.old
    sudo ln -s $installdir/bbat-nowin /usr/bin/bbat-nowin
else
    sudo ln -s $installdir/bbat-nowin /usr/bin/bbat-nowin
fi

echo 'Installed **** successfully. Run the command '
echo '  "bbat" for the GUI version or,'
echo '  "bbat-nowin" for the command line version.'

bitwidth=32

if [ $# -ne 0 ]; then
    if [ "$1" = "64" ]; then
        bitwidth=64
    fi
fi


path=$(which java 2>/dev/null)
if [ $? -ne 0 ]; then
    echo "Could not find java in your path. If you have trouble starting bbat, please add $bitwidth bit java to your path, or edit bbat.ini in $installdir directory and add the lines :"
    echo "-vm"
    echo "<path-to-your-libjvm.so>"
fi

exit 0
