#! /bin/bash
# This script will capture frames from the IR camera at a given interval,
# At that point it will capture snapshots of various data and
# write them to a CSV file as well as add the text to the frame itself.

if [ ${#} -lt 1 ]; then
  echo "usage: ${0} <device>"
  exit 1
fi

#Interval of inactivity, defined in seconds.
Interval=60

#Verbose logging (e.g. spew tons of garbage to console)
#0 = off, else = on
log=1

echo "Morpheus sleep lab v0.1a"
echo "------------------------"
echo ""
echo "This is a complete work in progress. Do not take results seriously."
echo "Verbose logging: $log"
echo ""
echo "Beginning..."

#configure serial port
if [ $(uname) = "Darwin"]; then
  echo "isDarwin"
else
  stty 38400 clocal < ${1}
fi

#spawn separate thread to collect serial port data
cat ${1} > serial_port_data &

#grab the pid so we can kill it when we exit
serialportcollector=$!

#capture EEG data from serial port also.. (code borrowed from bwview cause they're awesome)
#DEV=/dev/ttyS0 [ ! -z "$1" ] && DEV=$1
DEV=/dev/ttyS0

stty -F $DEV sane 57600 intr undef quit undef \
    erase undef kill undef eof undef eol undef eol2 \
    undef start undef stop undef susp undef rprnt undef \
    werase undef lnext undef flush undef min 1 time 0 \
    ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig \
    -icanon -iexten -echo -echoe -echok -echoctl -echoke

cat $DEV > eegtemp.dat & CATPID=$!

#Create variable to indicate all is well. We quit out of loop if
#something breaks.
ok=1

while [ $ok -eq 1 ];
do
    # TODO: Record delta hour, mins, seconds since we began
    # grab and log timestamp
    Timestamp=`date +%Y%m%d%H%M%S`
    if [ $log -ne 0 ]; then echo "Timestamp is $Timestamp"; fi

    # capture single frame
    if [ $log -ne 0 ]; then echo "Capturing frame to temp.jpeg"; fi
    rm temp.jpeg
    streamer -j 95 -o temp.jpeg

    # check to make sure frame got captured
    if [ -e temp.jpeg ]; then
        if [ $log -ne 0 ]; then echo "temp.jpeg exists, continuing..."; fi
    else
        ok=0;
        echo "Frame does not exist. Check to see if the webcam is in use by another application"
    fi

    # TODO: Populate other fields here
    # capture other relevant data here. In the future, this will tail
    # the most recent data from the serial port data we've been collecting 
    # and place it on the picture.

    # slap text on captured frame
    if [ $log -ne 0 ]; then echo "Labelling data onto image..."; fi
    caption=`date`
    caption="$caption \n This is a test"
    montage -geometry +0+0 -background black -fill white -label "$caption" -quality 95 temp.jpeg temp2.jpeg

    # rename frame to final value
    mv temp2.jpeg $Timestamp.jpeg

    # sleep (much like the person we're watching)
    if [ $log -ne 0 ]; then echo "Sleeping for $Interval seconds..."; fi
    sleep $Interval
done

#kills the cat statement for HRM and EOM
kill $serialportcollector
#kills the cat statement for EEG
kill $CATPID

exit 0

stty -F $DEV sane 57600 intr undef quit undef \
    erase undef kill undef eof undef eol undef eol2 \
    undef start undef stop undef susp undef rprnt undef \
    werase undef lnext undef flush undef min 1 time 0 \
    ignbrk -brkint -icrnl -imaxbel -opost -onlcr -isig \
    -icanon -iexten -echo -echoe -echok -echoctl -echoke
