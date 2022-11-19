# was-practice

2022-11-20_2
Thread를 새로 생성할때마다
독립적인 스택메모리를 할당 받는데 이건 상당히 비싼 작업입니다.
따라서 사용자의 요청이 있을때마다 Thread를 생성하게 된다면 성능이 매우 떨어집니다.
요청이 몰릴수록 Thread를 많이 생성하게 될거고 메모리 할당작업처럼 비싼작업이 매우 많이 일어나게 됩니다.
cpu 패스트스위칭 횟수가 많아지고 cpu와 메모리 사용량이 증가되어 최악의 경우에 서버의 리소스가 감당하지 못하게 되서
서버가 다운될수 있는 가능성이 있습니다.

=> 수정사항
스레드를 이미 고정된 만큼 갯수를 생성해두고 이를 재활용하는 Thead Pool 개념을 적용해서 안정적인 서비스가 가능하도록 작업

2022-11-20_1
메인 Thread로만 작업을 하다 blocking이 걸리게 된다면
다음 클라이언트의 요청이 해당 클라이언트의 요청이 끝날때까지 기다려야 한다는
끔찍한 단점이 있습니다.

=> 수정사항
해당요청이 클라이언트의 요청이 올때마다 별도의 메인Thread가 아닌
별도 Thread에서 수행할수 있도록 작업


2022-11-19
느낀점

제가 강의를 들으면서 was를 직접 생성하는 과정을 코딩하며 프로그램이 어떤식으로 돌아가는지 알게 되어 매우 뿌듯한 느낌이 들었습니다.


was는 서블릿컨테이너에서 웹 서버와 소켓을 만들고 통신하는 과정을 대신 처리하는 역할을 해줍니다.


저희가 여태껏 스프링 프레임워크를 사용하며 비즈니스 로직에만 집중해서 이러한 사실에 대해 잘 몰랐지만 이번강의를 통해 Web application의 원리에 대해 더 잘 이해하게 된것 같습니다.



강의내용을 보고 정리한 내용입니다.

소켓통신(톰캣을 직접 생성,was를 직접 생성)으로 서버가 클라이언트의 요청을 받아 queryString 에 있는 값으로 사칙연산을 만들었습니다.


http프로토콜의 규칙에 맞게 httpRequest를 만들고

httpResponse 규칙에 맞게끔 클라이언트의 요청을 전달해주는 프로그램을 만들었습니다.


예) 서버가 가동되는 상태에서

클라이언트가 http://localhost:8080/calculate?operand1=10&operator=-&operand2=55 이런 요청을 보낼 시


operand1 이 첫번쨰 값

operator이 연산자 구분

operand2가 두번쨰 값으로 설정하여


10-55 의 결과값으로 -45가 나오게 하였습니다.


먼저 CustomWebApplicationServer 클래스에서 서버 구동시

ServerSocket serverSocket = new ServerSocket(port)

서버 소켓 객체 생성


try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);
            

Socket clientSocket;

클라이언트 객체만 생성 후 대기 하다



Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

clientSocket = serverSocket.accept()) != null

클라이언트 서버 실행시 클라이언트와 서버의  연결이 성공



while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");


그 후 메인 Thread에서 작업을 진행합니다.

InputStream 객체로 클라이언트의 주소를 받아


InputStream in = clientSocket.getInputStream();

문자 입력 스트림에서 텍스트를 읽어


BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

HttpRequest에 담아


HttpRequest httpRequest = new HttpRequest(br);


int에 계산할 숫자와 String에 연산자를 담아 미리 만들어두었던 Calculator클래스에 담아 계산을 해준 후


int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                        String operator = queryStrings.getValue("operator");
                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                        int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                        byte[] body = String.valueOf(result).getBytes();

HttpResponse에 담아 클라이언트의 요청을 전달해주었습니다.



HttpResponse response = new HttpResponse(dos);
                        response.response200Header("application/json", body.length);
                        response.responseBody(body);


