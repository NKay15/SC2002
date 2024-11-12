#!/bin/bash

file_path="../data/Patient_List.txt"
patient_id=$1

sed -i '.bak' "/^$patient_id,/d" $file_path