val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

fun main() {
    val message = getMessage()
    val key = getKey()
    val table = getVigenereTable()

    val encryptedMessage = encrypt(message, key, table)
    val decryptedMessage = decrypt(encryptedMessage, key, table)

    println("Исходное сообщение: $message")
    println("Ключ: $key")
    println("Зашифрованное сообщение: $encryptedMessage")
    println("Расшифрованное сообщение: $decryptedMessage")
    println("Таблица Виженера:")
    printTable(table)
}

fun getMessage(): String {
    print("Введите сообщение: ")
    return readLine()!!.toUpperCase()
}

fun getKey(): String {
    print("Введите ключ: ")
    return readLine()!!.toUpperCase()
}

fun getVigenereTable(): Array<CharArray> {
    print("Использовать таблицу по умолчанию (да/нет): ")
    val answer = readLine()!!

    return if (answer.toLowerCase() == "да") {
        createDefaultTable()
    } else {
        createRandomTable()
    }
}

fun createDefaultTable(): Array<CharArray> {
    val table = Array(33) { CharArray(33) }

    for (i in 0 until 33) {
        for (j in 0 until 33) {
            val shift = (i + j) % 33
            table[i][j] = alphabet[shift]
        }
    }

    return table
}

fun createRandomTable(): Array<CharArray> {
    val table = Array(33) { CharArray(33) }

    val randomShifts = MutableList(33) { it }
    randomShifts.shuffle()

    for (i in 0 until 33) {
        for (j in 0 until 33) {
            val shift = (randomShifts[i] + j) % 33
            table[i][j] = alphabet[shift]
        }
    }

    return table
}

fun encrypt(message: String, key: String, table: Array<CharArray>): String {
    val encryptedMessage = StringBuilder()

    for (i in message.indices) {
        val messageChar = message[i]
        val keyChar = key[i % key.length]

        val rowIndex = alphabet.indexOf(keyChar)
        val columnIndex = alphabet.indexOf(messageChar)

        val encryptedChar = table[rowIndex][columnIndex]
        encryptedMessage.append(encryptedChar)
    }

    return encryptedMessage.toString()
}

fun decrypt(encryptedMessage: String, key: String, table: Array<CharArray>): String {
    val decryptedMessage = StringBuilder()

    for (i in encryptedMessage.indices) {
        val encryptedChar = encryptedMessage[i]
        val keyChar = key[i % key.length]

        val rowIndex = alphabet.indexOf(keyChar)

        val columnIndex = table[rowIndex].indexOf(encryptedChar)
        val decryptedChar = alphabet[columnIndex]

        decryptedMessage.append(decryptedChar)
    }

    return decryptedMessage.toString()
}

fun printTable(table: Array<CharArray>) {
    for (row in table) {
        for (element in row) {
            print("$element ")
        }
        println()
    }
}
