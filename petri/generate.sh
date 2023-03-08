#!/bin/bash
COMMON_PREFIX_DIR=./src/main/java/org/dhbw/ka/ml
jjtree -OUTPUT_DIRECTORY="$COMMON_PREFIX_DIR/generated" $COMMON_PREFIX_DIR/Petri.jjt
javacc -OUTPUT_DIRECTORY="$COMMON_PREFIX_DIR/generated" $COMMON_PREFIX_DIR/generated/Petri.jj