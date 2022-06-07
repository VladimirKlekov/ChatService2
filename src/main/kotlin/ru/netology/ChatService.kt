package ru.netology

import ru.netology.service.Chat
import ru.netology.service.ChatNotFoundError
import ru.netology.service.Message
import ru.netology.service.MessagesNotFoundError

class ChatService {
    //Делаю список для чатов
   val chats = mutableListOf<Chat>()
    private var messageId = 0L
    private var chatId = 0L

    //35 армия россии в украине

    fun deleteMessage(messageId: Int):Chat {

        return chats.removeAt(messageId)

    }

    fun readMessage(messageId: Int): List<Chat> {
        val chatCopy = chats.get(messageId)
           chats[messageId] = chatCopy .copy(
            readMessage = true)

        return chats

    }

    //P.S функции называются функциями области видимости (англ. scope functions). Всего их пять: let, run, with, apply, и also.

    //функция для создания сообщения
    fun createMessage(userId: Long, message: String, chatId: Long = 0): Message {
        //список чатов с индексом id = id чата
        //IndexOfFirst() - возвращает индекс первого элемента, соответствующего заданному предикату или -1, если таких элементов нет.
        val chatIndex = chats.indexOfFirst { it.id == chatId }

        //При вызове takeIf для объекта с предикатом этот объект будет возвращен,
        //если он соответствует предикату. В противном случае возвращается null
            .takeIf { it >= 0 }

            //run Контекстный объект доступен в качестве получателя (this). Возвращаемое значение - результат выполнения лямбды.
            ?: run {

                //List.lastOrNull() возвращает последний элемент или null, если список пуст.
                chats.add(Chat(chats.lastOrNull()?.id ?: chatId, emptyList()))
                chats.lastIndex
            }

        val currentChat = chats[chatIndex]
        val newMessage = Message(authorId = userId, id = messageId++, text = message)
        chats[chatIndex] = currentChat.copy(
            messages = currentChat.messages + newMessage
        )

        return newMessage
    }

    fun getMessages(chatId: Long, offset: Int, startFrom: Int): List<Message> =
//Функция single() возвращает единственный элемент потока, если поток содержит только один элемент.
        // Если поток не содержит элементов генерируется исключение NoSuchElementException, а если
        // в потоке больше одного элемента - исключение IllegalStateException.
        chats.singleOrNull { it.id == chatId }
                //let
            //Контекстный объект доступен в качестве аргумента (it). Возвращаемое значение - результат выполнения лямбды.
            .let { it?.messages ?: throw ChatNotFoundError() }
//Метод asSequence коллекций
//можно создать последовательность из объекта Iterable (например, из объектов типа List или Set)
            .asSequence()
//Для получения всех элементов, кроме указанного
// количества первых или последних элементов, используйте функции drop() и dropLast()
            .drop(startFrom)
                //Для получения определённого количества элементов, находящихся в начале коллекции
            // (начиная с первого и далее), используйте функцию take(). В свою очередь функция takeLast() вернёт список
            .take(offset)
//ifEmpty Возвращает этот массив, если он не пуст, или результат вызова функции defaultValue, если массив пуст.
            .ifEmpty { throw MessagesNotFoundError() }
                //вернуть список
            .toList()
}
