#To test app 
1. Start rabbitmq 


    docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:3-management

2. Enrich rabbit with definitions


`   curl -vX POST http://guest:guest@localhost:15672/api/definitions -d @definitions.json \
     --header "Content-Type: application/json"
`


#Support request to reprocess messages
[POST] http://localhost:8080/messages/reprocess/{amqp_messageId}

- http://localhost:8080/receipt/1 simulates DLQ
- http://localhost:8080/receipt/2 simulates Failed exchange

#Support request to retrieve failed messages
[GET] http://localhost:8080/messages

#Support request to reprocess messages
[POST] http://localhost:8080/messages/reprocess/{amqp_messageId}
