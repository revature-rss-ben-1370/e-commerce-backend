node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def scannerHome = tool 'Sonar-Scanner';
    withSonarQubeEnv('sonarserver') {
      sh "${scannerHome}/bin/sonar-scanner"
    }
  }
}