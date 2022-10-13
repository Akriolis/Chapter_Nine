interface FieldValidator <in T>{
    fun validate (input: T): Boolean
}

object DefaultStringValidator: FieldValidator<String>{
    override fun validate(input: String): Boolean = input.isNotEmpty()

}

object DefaultIntValidator: FieldValidator<Int>{
    override fun validate(input: Int): Boolean = input >= 0
}