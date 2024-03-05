#!/bin/bash
sed -i 'y/abcdefghijklmnopqrstuvwxyz|ABCDEFGHIJKLMNOPQRSTUVWXYZ/fghijklmnopqrstuvwxyzabcde|FGHIJKLMNOPQRSTUVWXYZABCDE/' "$1"