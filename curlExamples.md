## Examples for curl

> export TOPJAVA="http://172.16.1.11:8081/topjava/"

###Meals:

#### getAll:
> curl -i -X GET "$TOPJAVA/rest/meals"

#### getMeals with date and time filters:
> curl -i -X GET "$TOPJAVA/rest/meals/filter2?startTime=10:00&endTime=19:00&startDate=31.05.2015&endDate=31.05.2015"

#### getMeal by mealId:
> curl -i -X GET "$TOPJAVA/rest/meals/100002"

#### edit meal by mealId:
> curl -i -H "Content-Type: application/json" -X PUT -d '{"description":"testMeal","dateTime":"2017-12-20T11:12","calories":"100"}' "$TOPJAVA/rest/meals/100002"

#### create new meal:
> curl -i -H "Content-Type: application/json" -X POST -d '{"description":"testMeal","dateTime":"2017-12-22T11:12","calories":"100"}' "$TOPJAVA/rest/meals/"

#### delete by mealId:
> curl -i -X DELETE "$TOPJAVA/rest/meals/100002"

  