node {
  properties = readProperties file: 'pipeline/vars.properties'
  echo "Building Terraform in ${properties.environment} environment"
  stage('Preparation') {
    git 'https://github.com/hgsoloco/vaulta.git'

  }
  stage('Build') {
    
    environment = "prod"
      
    def nonprodsecrets = [
        [path: 'aws/creds/s3-ec2', engineVersion: 1, secretValues: [
            [envVar: 'akey', vaultKey: 'access_key'],
            [envVar: 'asecret', vaultKey: 'secret_key']]]
    ]

    def nonprodconfiguration = [vaultUrl: 'http://35.232.41.214:8200',
                        vaultCredentialId: 'genny',
                        engineVersion: 1]
    
    def prodsecrets = [
        [path: 'aws2/creds/prodaccount', engineVersion: 1, secretValues: [
            [envVar: 'akey', vaultKey: 'access_key'],
            [envVar: 'asecret', vaultKey: 'secret_key']]]
    ]

    def prodconfiguration = [vaultUrl: 'http://35.232.41.214:8200',
                        vaultCredentialId: 'james',
                        engineVersion: 1]  

    echo "Environment Deployment to ${properties.environment}"

    withVault([configuration: nonprodconfiguration, vaultSecrets: nonprodsecrets]) {
            sh 'chmod +x ./delay-vault-aws.sh && ./delay-vault-aws.sh'
            sh 'terraform init'
            sh 'terraform plan -var secret_key=$asecret -var access_key=$akey'
            sh 'terraform apply -var secret_key=$asecret -var access_key=$akey -auto-approve'
            sh 'terraform destroy -var secret_key=$asecret -var access_key=$akey -force'
   }
    }
    
  }
}