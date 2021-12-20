pipeline {

    agent any

    environment {
        TERRAFORM_DESTROY = 'Terraform destroy'
    }

    stages {

        stage('Terraform destroy') {
            steps {
                dir('/usr/share/terraform/instances/'){

                    sh 'terraform destroy --auto-approve'

                }
            }
        }

    }
}