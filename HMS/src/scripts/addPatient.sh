#!/bin/bash

file_path="../data/Patient_List.txt"

patient_id=$1
name=$2
dob=$3
gender=$4
blood_type=$5
email=$6
phone=$7
password=$8

sed -i '.bak' -e $'1a\\\n'"$patient_id,$name,$dob,$gender,$blood_type,$email,$phone,$password" $file_path
