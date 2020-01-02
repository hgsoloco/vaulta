node {

  def props = readProperties  file: 'pipeline/vars.properties'
  keys = props.keySet()
  for(key in keys) {
    value = props["${key}"]
    env."${key}" = "${value}"
  }

  echo "Building Terraform in ${environment} environment"
  stage('Preparation') {
    git 'https://github.com/hgsoloco/vaulta.git'
  }

  if ("${environment}" == 'nonprod') {
  stage('Build') {
      sh '''#!/bin/bash
        k=$(curl --header "X-Vault-Token: ${token}" \
        --request GET http://35.239.60.86:8200/v1/aws/creds/s3-ec2 | jq -r '.data.access_key,.data.secret_key')
        ak=$(echo $k | cut -d ' ' -f 1) && sk=$(echo $k | cut -d ' ' -f 2)
        chmod +x ./delay-vault-aws.sh && ./delay-vault-aws.sh
        terraform init
        terraform plan -var secret_key=$sk -var access_key=$ak
        terraform apply -var secret_key=$sk -var access_key=$ak -auto-approve
        terraform destroy -var secret_key=$sk -var access_key=$ak -force
      '''
      }
  }  

  if ("${environment}" == 'prod') {
    stage('Build') {
      sh '''#!/bin/bash
        k=$(curl --header "X-Vault-Token: ${token}" \
        --request GET http://35.239.60.86:8200/v1/aws/creds/prodaccount | jq -r '.data.access_key,.data.secret_key')
        ak=$(echo $k | cut -d ' ' -f 1) && sk=$(echo $k | cut -d ' ' -f 2)
        chmod +x ./delay-vault-aws.sh && ./delay-vault-aws.sh
        terraform init
        terraform plan -var secret_key=$sk -var access_key=$ak
        terraform apply -var secret_key=$sk -var access_key=$ak -auto-approve
        terraform destroy -var secret_key=$sk -var access_key=$ak -force
      '''
    }
  }  

}








// sh "echo \${some_var}"
// ak=$(echo ${k} | cut -d ' ' -f 1) && sk=$(echo ${k} | cut -d ' ' -f 2)
// s.0luSVWmp77ReJpy2VwcAxD3T
// bk=$(echo $ak | cut -d ' ' -f 1) && sk=$(echo $ak | cut -d ' ' -f 2)
// ak=$( curl --header "X-Vault-Token: s.0luSVWmp77ReJpy2VwcAxD3T" 
// --request GET http://35.232.41.214:8200/v1/aws/creds/s3-ec2 | jq -r '.data.access_key,.data.secret_key')

//    sh '''#!/bin/bash
//    k=$(curl --header "X-Vault-Token: ${properties.token}" \
//    --request GET http://35.232.41.214:8200/v1/aws/creds/s3-ec2 | jq -r '.data.access_key,.data.secret_key')
//    ak=$(echo $k | cut -d ' ' -f 1) && sk=$(echo $k | cut -d ' ' -f 2)
//    chmod +x ./delay-vault-aws.sh && ./delay-vault-aws.sh
//    terraform init
//    terraform plan -var secret_key=$sk -var access_key=$ak
//    terraform apply -var secret_key=$sk -var access_key=$ak -auto-approve
//    terraform destroy -var secret_key=$sk -var access_key=$ak -force
//    '''

// echo 'token is ${properties.token}'



//stage('Build') {
//    sh '''#!/bin/bash
//      k=$(curl --header "X-Vault-Token: ${token}" \
//      --request GET http://34.69.216.77:8200/v1/aws/creds/s3-ec2 | jq -r '.data.access_key,.data.secret_key')
//      ak=$(echo $k | cut -d ' ' -f 1) && sk=$(echo $k | cut -d ' ' -f 2)
//      chmod +x ./delay-vault-aws.sh && ./delay-vault-aws.sh
//      terraform init
//      terraform plan -var secret_key=$sk -var access_key=$ak
//      terraform apply -var secret_key=$sk -var access_key=$ak -auto-approve
//      terraform destroy -var secret_key=$sk -var access_key=$ak -force
//    '''
//  }  
//testwithpolling2