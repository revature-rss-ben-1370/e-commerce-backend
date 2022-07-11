node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv('sonarserver') {
      sh "mvn sonar:sonar"
    }
  }
}