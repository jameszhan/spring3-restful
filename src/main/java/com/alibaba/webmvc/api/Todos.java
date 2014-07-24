package com.alibaba.webmvc.api;

import com.alibaba.webmvc.annotation.CSRFValidate;
import com.alibaba.webmvc.extension.ResourceNotFoundException;
import com.alibaba.webmvc.extension.WebflowException;
import com.alibaba.webmvc.model.Todo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 4:23 PM
 */
@Controller
@RequestMapping("/api/todos")
public class Todos {

    private static AtomicLong nextId = new AtomicLong(0);

    public static final Cache<Long, Todo> todos = CacheBuilder.newBuilder().build();
    static {
        todos.put(nextId.incrementAndGet(), Todo.build(nextId.get(), "Concrete Mathematics", "Classic"));
        todos.put(nextId.incrementAndGet(), Todo.build(nextId.get(), "Languages and Machines", "Classic"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("todos", todos.asMap().values());
        return mv;
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public ModelAndView newTodo() {
        return new ModelAndView("new");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        Todo todo = todos.getIfPresent(id);
        if (todo == null) {
            throw new ResourceNotFoundException();
        }
        ModelAndView mv = new ModelAndView("edit");
        mv.addObject("todo", todo);
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("id") Long id) {
        Todo todo = todos.getIfPresent(id);
        if (todo == null) {
            throw new ResourceNotFoundException();
        }
        ModelAndView mv = new ModelAndView("show");
        mv.addObject("todo", todo);
        return mv;
    }

    @CSRFValidate
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Todo create(Todo todo){
        if (todo == null) {
            throw new WebflowException("405", "Create todo fail.").addParam("target", todo);
        } else {
            long id = nextId.incrementAndGet();
            todo.setId(id);
            todos.put(id, todo);
            return todo;
        }
    }

    @CSRFValidate
    @RequestMapping(value = "/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    @ResponseBody
    public Todo update(@PathVariable("id") Long id, Todo props) {
        Todo todo = todos.getIfPresent(id);
        if (todo == null) {
            throw new WebflowException("404", "Resource not found.").addParam("target", id);
        } else {
            todo.update(props);
            return todo;
        }
    }

    @CSRFValidate
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Todo delete(@PathVariable("id") Long id) {
        Todo originalTodo = todos.getIfPresent(id);
        if (originalTodo == null) {
            throw new WebflowException("404", "Resource not found.").addParam("target", id);
        } else {
            todos.invalidate(id);
            return originalTodo;
        }
    }


}
