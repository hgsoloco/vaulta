node {
  properties = readProperties file: 'pipeline/vars.properties'
  echo "Building Terraform in ${properties.environment} environment"
  stage('Preparation') {
    git 'https://github.com/hgsoloco/vaulta.git'

  }
  stage('Build') {
    sh '''#!/bin/bash
    ak=$(curl --header "X-Vault-Token: s.0luSVWmp77ReJpy2VwcAxD3T" \
    --request GET http://35.232.41.214:8200/v1/aws/creds/s3-ec2 | jq -r '.data.access_key,.data.secret_key')
    echo $ak
    
    '''

    
    }
    
}


// bk=$(echo $ak | cut -d ' ' -f 1) && sk=$(echo $ak | cut -d ' ' -f 2)
// ak=$( curl --header "X-Vault-Token: s.0luSVWmp77ReJpy2VwcAxD3T" 
// --request GET http://35.232.41.214:8200/v1/aws/creds/s3-ec2 | jq -r '.data.access_key,.data.secret_key')