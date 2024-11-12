#!/bin/bash

file_path="../data/Patient_List.txt"
patient_id=$1
new_email=$2

awk -F, -v OFS=, -v id="$patient_id" -v email="$new_email" -v phone="$new_phone" '{
    if($1 == id) { $6 = email }
}1' $file_path > temp && mv temp $file_path