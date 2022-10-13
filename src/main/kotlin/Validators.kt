import kotlin.reflect.KClass

object Validators {
    private val validators =
        mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun <T: Any> registerValidator(
        kClass: KClass<T>, fieldValidator: FieldValidator<T>){
        validators[kClass] = fieldValidator
    }

    @Suppress ("UNCHECKED_CAST")
    operator fun <T: Any> get(kClass: KClass<T>): FieldValidator<T> =
        validators[kClass] as? FieldValidator<T>
            ?: throw IllegalArgumentException(
                "No validator for ${kClass.simpleName}"
            )
}