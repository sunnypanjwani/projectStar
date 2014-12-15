#!/bin/bash

MMDDHHmmSS=`/bin/date +%m%d%H%M%S`

REQUEST='<?xml version="1.0" encoding="UTF-8"?>
<EditProfileRequest><userProfilesId>1</userProfilesId><talentType>singer</talentType><profileName>edited awesome sunny</profileName></EditProfileRequest>'

URL=http://localhost:8080/stars_service-0.0.1/v1/stars/profile/editProfile

echo curl -v -H "Content-Type: application/xml" -k -d "$REQUEST" $URL
curl -v -H "Content-Type: application/xml" -k -d "$REQUEST" $URL -L

echo
