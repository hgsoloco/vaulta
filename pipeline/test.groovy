node {
  load "vars.properties"
  echo "${environment}"
  stage('Preparation') {
    git 'https://github.com/hgsoloco/vaulta.git'

  }
  stage('Build') {
  echo "hi"
    
    
  }
}