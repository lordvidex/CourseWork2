package ru.itis.dis;

import ru.itis.dis.handlers.HttpHandler;
import ru.itis.dis.handlers.ServerFileHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 1:18 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Context {
    // stores the URL handlers and their paths
    private final Map<String, HttpHandler> handlers;

    // session map stores the sessionId of a user with his data
    private final Map<String, Session> sessionMap;

    public Context() {
        this.handlers = new HashMap<>();
        this.sessionMap = new HashMap<>();
    }

    // adds the context to the handlers map
    public void createContext(String path, HttpHandler handler) {
        handlers.put(path,handler);
    }

    // for adding /404 handler for files not found
    public void createNotFoundContext(HttpHandler handler) {
        createContext("/404", handler);
    }

    // used to get the session for a user, it can be null
    public Session getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public void setSession(String key, Session session){
        sessionMap.entrySet().removeIf((e) -> e.getValue().equals(session));
        this.sessionMap.put(key,session);
    }

    /**
     * Returns an HttpHandler from the map and returns a not found page
     * if the path does not exist or the root page if there is no mapped 404 page
     * @param path the path from an http request
     * @return the handler for this path
     */
    public HttpHandler getContext(String path) {
        String _path;
        if(path.endsWith("/")) {
            _path = path.substring(0,path.length()-1);
        } else {
            _path = path+"/";
        }

        // resource handlers first
        if(path.endsWith(".css") || path.endsWith(".js") || path.endsWith("html")) {
            return new ServerFileHandler();
        }

        if(handlers.containsKey(path)) {
            return handlers.get(path);
        } else if(handlers.containsKey(_path)) {
            return handlers.get(_path);
        } else if (handlers.containsKey("/404")){
            return handlers.get("/404");
        } else {
            return handlers.get("/");
        }
    }

}
