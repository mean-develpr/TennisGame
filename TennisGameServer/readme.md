curl -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/score"  | jq .
curl -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/point/1"  | jq .
curl -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/point/0"  | jq .

