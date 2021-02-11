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
        System.out.println(Thread.currentThread());
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String url = readHeaders(input);

            if (url.equals("/user")) {
//                handleProductsURL();
            } else if (url.equals("/todos")) {
//                handleTodosURL();
            } else if (url.equals("/index.html")) {
                var output = new PrintWriter(socket.getOutputStream());

//                DAO dao = new DAO();
//
//                List<User> productList = dao.getALL();

                File file = new File("web" + File.separator + url);
                byte[] page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:" + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                dataOut.write(page);
                dataOut.flush();
                socket.close();
            } else if (url.equals("/cat.png")) {
                var output = new PrintWriter(socket.getOutputStream());

                File file = new File("web" + File.separator + url);
                byte[] page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:" + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                dataOut.write(page);
                dataOut.flush();
                socket.close();
            }
            else if (url.equals("/stylesheet.css")) {
                var output = new PrintWriter(socket.getOutputStream());

                File file = new File("web" + File.separator + url);
                byte[] page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:" + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                dataOut.write(page);
                dataOut.flush();
                socket.close();
            }
            else if (url.equals("/javascriptfile.js")) {
                var output = new PrintWriter(socket.getOutputStream());

                File file = new File("web" + File.separator + url);
                byte[] page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:" + contentType);  //application/json
                output.println("");
                //output.print(page);
                output.flush();
                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                dataOut.write(page);
                dataOut.flush();
                socket.close();
            }
            else {
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
//    private static void handleTodosURL() {
//
//
//    }

    private static String readHeaders(BufferedReader input) throws IOException {
        String requestedUrl = "";
        while (true) {
            String headerLine = input.readLine();
            if( headerLine.startsWith("GET")){
                requestedUrl = headerLine.split(" ")[1];
            }
            else if(headerLine.startsWith("HEAD")){
                requestedUrl = headerLine.split(" ")[1];
            }
            else if(headerLine.startsWith("POST")){
                requestedUrl = headerLine.split(" ")[1];
            }
            //kod som gör man kan sätta in sökparametrar. typ user?=börje osv.

            System.out.println(headerLine);
            if (headerLine.isEmpty())
                break;
        }
        return requestedUrl;
    }

    private static void createJsonResponse() {
        var todos = new Todos();
        todos.todos = new ArrayList<>();
        todos.todos.add(new Todo("1", "Todo 1", false));
        todos.todos.add(new Todo("2", "Todo 2", false));

        JsonConverter converter = new JsonConverter();

        var json = converter.convertToJson(todos);
        System.out.println(json);
    }

    private static String handleProductsURL(){
        return "";
    }
}

