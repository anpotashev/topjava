Examples for bash-shell


export TOPJAVA="http://172.16.1.11:8081/topjava/"

Meals:
- getAll:
curl -i -X GET "$TOPJAVA/rest/meals"

- getMeals with date and time filters:
color.Blue(curl -i -X GET "$TOPJAVA/rest/meals/filter2?startTime=10:00&endTime=19:00&startDate=31.05.2015&endDate=31.05.2015")

- getMeal by mealId:
color.Blue(curl -i -X GET "$TOPJAVA/rest/meals/100002")

- edit meal by mealId:
color.Blue(curl -i -H "Content-Type: application/json" -X PUT -d '{"description":"testMeal","dateTime":"2017-12-20T11:12","calories":"100"}' "$TOPJAVA/rest/meals/100002")

- create new meal:
color.Blue(curl -i -H "Content-Type: application/json" -X POST -d '{"description":"testMeal","dateTime":"2017-12-22T11:12","calories":"100"}' "$TOPJAVA/rest/meals/")

There is location address of created resource:
HTTP/1.1 201
color.red()Location: http://172.16.1.11:8081/topjava/rest/meals/100010)
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 26 Dec 2017 19:43:07 GMT

- delete by mealId:
color.Blue(curl -i -X DELETE "$TOPJAVA/rest/meals/100002")  