package com.alibaba.webmvc.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 11:41 PM
 */
public class Todo {

    private long id;
    private String name;
    private String desc;
    private int priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Todo update(Todo props){
        if (props.name != null) {
            this.name = props.name;
        }
        if (props.desc != null) {
            this.desc = props.desc;
        }
        if (props.priority != this.priority) {
            this.priority = props.priority;
        }
        return this;
    }

    public static Todo build(long id, String name, String desc) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setName(name);
        todo.setDesc(desc);
        return todo;
    }
}
