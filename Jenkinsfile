//Requires jenkins git parameter plugin
//Also requires pipeline-utility-steps-plugin
pipeline {
    agent any
    parameters {
        gitParameter branchFilter: 'origin/(.*)', defaultValue: 'next-release', name: 'BRANCH', type: 'PT_BRANCH'
    }
    stages {
        //Pull latest next-release branch
        stage('SCM Checkout') {
            steps {
                git branch: "${params.BRANCH}", url: 'https://github.com/Camphul/Trampoline.git'
            }
        }
        stage('Compile-package') {
            steps {
                sh 'mvn clean package -Pjenkins-pipeline'
            }
        }
        stage('Deploy-repository') {
            //Only do this when the maven pom version is not a snapshot version.
            when {
                expression {
                    pom = readMavenPom file: 'pom.xml'
                    isSnapshot = pom.version.endsWith('-SNAPSHOT')
                    return isSnapshot ==~ /(?i)(FALSE)/
                }
            }
            steps {
                sh 'mvn deploy -Pjenkins-pipeline'
            }
        }
    }
}