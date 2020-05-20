#!/usr/bin/env groovy

@Library('lco-shared-libs@0.1.0') _

pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				sh 'mvn compile'
			}
		}
		stage('Deploy') {
			steps {
				sh 'mvn deploy'
			}
		}
	}
	post {
		always { postBuildNotify() }
	}
}
