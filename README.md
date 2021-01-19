# PaytmLabs SDE Challenge

## Coding Question

Write an interface for a data structure that can provide the moving average of the last N elements added, add elements to the structure and get access to the elements. Provide an efficient implementation of the interface for the data structure.

### Minimum Requirements

1. Provide a separate interface (IE `interface`/`trait`) with documentation for the data structure
2. Provide an implementation for the interface
3. Provide any additional explanation about the interface and implementation in a README file.

## Design Question

Design A Google Analytic like Backend System.
We need to provide Google Analytic like services to our customers. Please provide a high level solution design for the backend system. Feel free to choose any open source tools as you want.

### Requirements

1. Handle large write volume: Billions of write events per day.
2. Handle large read/query volume: Millions of merchants wish to gain insight into their business. Read/Query patterns are time-series related metrics.
3. Provide metrics to customers with at most one hour delay.
4. Run with minimum downtime.
5. Have the ability to reprocess historical data in case of bugs in the processing logic.


## Solutions:

## Coding Question
Since the requirement is to keep attract of last N elements and calculate the moving average of the last N elements. 

I decided to use a queue to maintain the last N elements. As queue can do First-In-First-Out. 

And in the meanwhile, I need to keep track up the current queue size so that after we add one element the queue size becomes bigger than N,then we poll the first element of the queue. Then we calculate the current moving average of the current elements of queue.

Lastly, I create a test class under PaytmLabsSdeChallengeApplicationTests.java to test my code.

•	Time Complexity: O(1), as we are just add element into queue and calculate the moving average without any loop or recursive actions.
•	Space Complexity: O(N), where N is the size of the moving window.

## Design Question
As the volume of the input and out would be very large. It would be very efficient to use distribute system to handle this case.

Since the input data could come from various resource, it would make sense to use a sub/pub pattern to collect the data and feed to the logic layer. 

As kafka is famous of it’s loose coupling ability and high scalability, I choose to use kafka. 

In addition, because the Apache Kafka has the most mature community I choose to use Apache Kafka.

After we collect the data, we need to have ability to process the data and deliver the result of processing with at most one hour delay. 

Base on this requirement I think it should use Streaming Processing instead of Batching processing. 

Since the income of the data is unbounded stream and it is time domain sensitive, I would choose spark streaming to handle data processing part.

In summary, I would use spring boot+kafka+spark streaming to develop this application.


