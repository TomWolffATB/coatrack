#!/bin/bash

cd "$PWD" || exit

for browser in firefox edge chrome opera
do
  . ./stop.sh
  . ./run.sh $browser
done

. ./stop.sh