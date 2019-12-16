node {

  stage('Preparation') {
    sh 'pwd'
    sh 'pwd'
    sh 'ls'
    sh 'pwd'
    properties = readProperties file: 'pipeline/vars.properties'
    echo "Running build ${JOB_NAME}"

  }

}