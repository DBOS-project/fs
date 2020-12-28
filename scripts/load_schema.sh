#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/..

sqlcmd < sql/create_tables.sql
./scripts/update_stored_procedures.sh 
