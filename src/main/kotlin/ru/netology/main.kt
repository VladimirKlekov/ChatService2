package ru.netology

fun main(){
    //создал сервис
val service = ChatService()
    //создал сообщения и сохранил в чат
    service.createMessage(1,"Первое сообщение",1)
    service.createMessage(2,"Второе сообщение", 1)


    //проверка списка

    println(service.chats)
    service.readMessage(0)
    println(service.chats)
    service.deleteMessage(0)
    println(service.chats)




}