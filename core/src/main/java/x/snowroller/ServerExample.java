package x.snowroller;

import x.snowroller.fileutils.FileReader;
import x.snowroller.models.Todo;
import x.snowroller.models.Todos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerExample {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            //TCP/IP
            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println(Thread.currentThread());

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {
        System.out.println("Current thread running: "+Thread.currentThread());

        //Work that we want the threads to do:
        BufferedReader input = null;

        try {
            //We read characters from the client(insomnia) via input stream in the socket
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String url = input.readLine();
            String requestedUrl = url.split(" ")[1];
            String requestType = url.split(" ")[0];

            File file = new File("core/web" + File.separator + requestedUrl);
            byte[] page;
            if (requestType.equals("POST")) {
                System.out.println("POST-request identified :)");            }

            if (requestedUrl.equals("/index.html")) {
                //we get character output stream to client (for headers)
                var output = new PrintWriter(socket.getOutputStream());

                page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length: " + page.length);
                output.println("Content-Type: " + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());

                if (requestType.equals("GET")) {
                    dataOut.write(page);
                    dataOut.flush();
                    socket.close();
                } else if (requestType.equals("HEAD")) {
                    dataOut.flush();
                    socket.close();
                }
            } else if (requestedUrl.equals("/cat.png")) {
                var output = new PrintWriter(socket.getOutputStream());

                page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:" + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                if (requestType.equals("GET")) {
                    dataOut.write(page);
                    dataOut.flush();
                    socket.close();
                } else if (requestType.equals("HEAD")) {
                    dataOut.flush();
                    socket.close();
                }
            } else if (requestedUrl.equals("/stylesheet.css")) {
                var output = new PrintWriter(socket.getOutputStream());

                page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:" + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                if (requestType.equals("GET")) {
                    dataOut.write(page);
                    dataOut.flush();
                    socket.close();
                } else if (requestType.equals("HEAD")) {
                    dataOut.flush();
                    socket.close();
                }
            } else if (requestedUrl.equals("/javascriptfile.js")) {
                var output = new PrintWriter(socket.getOutputStream());

                page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:" + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                if (requestType.equals("GET")) {
                    dataOut.write(page);
                    dataOut.flush();
                    socket.close();
                } else if (requestType.equals("HEAD")) {
                    dataOut.flush();
                    socket.close();
                }
            } else {
                var output = new PrintWriter(socket.getOutputStream());

                output.println("HTTP/1.1 404");
                output.println("Content-Length: 0");
                output.flush();
            }
            var dataOut = new BufferedOutputStream(socket.getOutputStream());
//            dataOut.write(page);
            dataOut.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    nedanstående metod används ej. probeContent tror jag gör jobbet istället?
//    private static String contentType(String requestedUrl) {
////        if (requestedUrl.endsWith(".html")) {
////            return "text/html";
////        } else if (requestedUrl.endsWith(".png")) {
////            return "image/png";
////        } else if (requestedUrl.endsWith(".pdf")) {
////            return "application/pdf";
////        } else if (requestedUrl.endsWith(".js")) {
////            return "text/javascript";
////        } else if (requestedUrl.endsWith(".css")) {
////            return "text/css";
////        } else return "text/plain";
////    }

//    private static void handleTodosURL() {
//
//
//    }

//    private static String readHeaders(BufferedReader input) throws IOException {
//        String requestedUrl = "";
//        while (true) {
//            String headerLine = input.readLine();
//            if( headerLine.startsWith("GET")){
//                requestedUrl = headerLine.split(" ")[1];
//            }
//            else if(headerLine.startsWith("HEAD")){
//                requestedUrl = headerLine.split(" ")[1];
//            }
//            else if(headerLine.startsWith("POST")){
//                requestedUrl = headerLine.split(" ")[1];
//            }
//            //kod som gör man kan sätta in sökparametrar. typ user?=börje osv.
//
//            System.out.println(headerLine);
//            if (headerLine.isEmpty())
//                break;
//        }
//        return requestedUrl;
//    }

    private static void createJsonResponse() {
        var todos = new Todos();
        todos.todos = new ArrayList<>();
        todos.todos.add(new Todo("1", "Todo 1", false));
        todos.todos.add(new Todo("2", "Todo 2", false));

        JsonConverter converter = new JsonConverter();

        var json = converter.convertToJson(todos);
        System.out.println(json);
    }
}

//    private static String handleProductsURL(){
//        return "";
//    }
//}

