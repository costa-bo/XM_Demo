if [[ "$1" == "-h" || "$1" == "--help" ]]; then
  echo "Usage: ./launch-test.sh <TC> <environment>"
	exit
fi

# Run ALL tests
if [ "$1" == "" ]; then
	mvn clean verify -Dfailsafe.rerunFailingTestsCount=1
	mvn serenity:aggregate
	exit
fi

# Run SPECIFIC test
if [ "$1" != "" -a "$2" == "" ]; then
  var="$1"
  replace="__"
  testcaseID=${var//-/$replace}
	mvn clean verify -Dtest.prefix="$testcaseID"
	mvn serenity:aggregate
	exit
# Run SPECIFIC test at SPECIFIC environment from serenity.conf
elif [ "$1" != "" -a "$2" != "" ]; then
  var="$1"
  replace="__"
  testcaseID=${var//-/$replace}
  echo mvn clean verify -Denvironment="$2" -Dtest.prefix="$testcaseID"
  sleep 3
	mvn clean verify -Denvironment="$2" -Dtest.prefix="$testcaseID"
	mvn serenity:aggregate
	exit
fi