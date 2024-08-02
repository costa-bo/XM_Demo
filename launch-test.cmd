echo off

set TEST_NAME=%1

REM Run hps demo
mvn clean verify -Dtest.prefix=TC_

pause
cmd /k
set /p=

goto comment
REM Run ALL tests
if [%TEST_NAME%]==[] (
	mvn clean verify -Dfailsafe.rerunFailingTestsCount=1
	mvn serenity:aggregate
	exit
)

REM Run SPECIFIC test
if not [%TEST_NAME%]==[] (
	mvn clean verify -Dtest.prefix="%TEST_NAME:-=__%"
	mvn serenity:aggregate
	exit
)
:comment

echo on
