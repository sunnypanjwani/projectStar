#!/bin/bash

MMDDHHmmSS=`/bin/date +%m%d%H%M%S`

REQUEST='<?xml version="1.0" encoding="UTF-8"?>
<AddProfileRequest><userId>131</userId><talentType>singer</talentType><profileName>awesome sunny</profileName></AddProfileRequest>'

URL=http://localhost:8080/stars_service-0.0.1/v1/stars/profile/addProfile

echo curl -v -H "Content-Type: application/xml" -k -d "$REQUEST" $URL
curl -v -H "Content-Type: application/xml" -k -d "$REQUEST" $URL -L

echo
