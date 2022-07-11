node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'Maven';
    withSonarQubeEnv('sonarserver') {
      sh "${mvn}/bin/mvn sonar:sonar"
    }
  }
}