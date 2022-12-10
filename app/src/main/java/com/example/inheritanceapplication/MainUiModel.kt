package com.example.inheritanceapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList


data class MainUiModel(
    val gender: MutableState<Genders> = mutableStateOf(Genders.MALE),
    val properties: SnapshotStateList<PropertyDetails> = SnapshotStateList(),

    val inheritors: List<Map<RelationKey, List<InheritorDetails>>> = listOf(

        if (gender.value == Genders.FEMALE) {
            mapOf(
                RelationKey.HUSBAND_SIDE to listOf(InheritorDetails(Relationship.HUSBAND))
            )
        } else {
            mapOf(
                RelationKey.WIFE_SIDE to listOf(InheritorDetails(Relationship.WIFE))
            )
        },


        mapOf(
            RelationKey.FATHER_SIDE to listOf(
                InheritorDetails(Relationship.FATHER),
                InheritorDetails(Relationship.GRANDFATHER),
                InheritorDetails(Relationship.FATHER_COUSIN),
                InheritorDetails(Relationship.FATHER_COUSIN_SON)
            )
        ),

        mapOf(
            RelationKey.MOTHER_SIDE to listOf(InheritorDetails(Relationship.MOTHER))
        ),

        mapOf(
            RelationKey.DAUGHTER_SIDE to listOf(InheritorDetails(Relationship.DAUGHTER))
        ),

        mapOf(
            RelationKey.SON_SIDE to listOf(InheritorDetails(Relationship.SON))
        ),

        mapOf(
            RelationKey.SISTER_SIDE to listOf(InheritorDetails(Relationship.SISTER))
        ),

        mapOf(
            RelationKey.BROTHER_SIDE to listOf(
                InheritorDetails(Relationship.BROTHER),
                InheritorDetails(Relationship.BROTHER_SON)
            ),
        ),

        mapOf(
            RelationKey.UNCLE_SIDE to listOf(
                InheritorDetails(Relationship.UNCLE),
                InheritorDetails(Relationship.COUSIN),
                InheritorDetails(Relationship.COUSIN_SON)
            ),
        ),

        mapOf(
            RelationKey.GRANDSON_SIDE to listOf(InheritorDetails(Relationship.GRANDSON))
        ),

        mapOf(
            RelationKey.GRANDDAUGHTER_SIDE to listOf(InheritorDetails(Relationship.GRANDDAUGHTER))
        ),

        mapOf(
            RelationKey.STEPMOTHER_SIDE to listOf(
                InheritorDetails(Relationship.MOTHER_SISTER),
                InheritorDetails(Relationship.MOTHER_BROTHER),
                InheritorDetails(Relationship.MOTHER_GRANDMOTHER)
            ),
        ),

        mapOf(
            RelationKey.STEPFATHER_SIDE to listOf(
                InheritorDetails(Relationship.FATHER_GRANDMOTHER),
                InheritorDetails(Relationship.STEPFATHER_DAUGHTER),
                InheritorDetails(Relationship.STEPFATHER_SON),
                InheritorDetails(Relationship.FATHER_UNCLE),
                InheritorDetails(Relationship.STEPFATHER_COUSIN_SON)
            ),
        ),

        mapOf(
            RelationKey.STEPBROTHER_SIDE to listOf(InheritorDetails(Relationship.STEPBROTHER))
        )
    )
) {
    fun totalAmount(): Long = properties.sumOf { it.amountInCurrency.value }


    fun setGenderType(type: Genders) {
        gender.value = type
    }

    fun addProperty(type: PropertyType) {
        properties.add(
            PropertyDetails(
                type = type,
                amountInCurrency = mutableStateOf(0)
            )
        )
    }

    fun remove(index: Int) {
        properties.removeAt(index)
    }
}

data class PropertyDetails(
    val type: PropertyType,
    val amountInCurrency: MutableState<Long>
) {
    fun getIcon(): Int {
        return when (type) {
            PropertyType.CASH -> R.drawable.baseline_monetization_on_24
            PropertyType.LAND -> R.drawable.baseline_forest_24
            PropertyType.CAR -> R.drawable.baseline_car_24
            PropertyType.COMPANY -> R.drawable.baseline_business_24
            PropertyType.GOLD -> R.drawable.baseline_gold_24
        }
    }

    fun canHaveCashAmount() =
        listOf(PropertyType.CASH, PropertyType.LAND, PropertyType.CAR).contains(type)
}

data class InheritorDetails(
    val relationship: MutableState<Relationship>,
    val amount: MutableState<Int> = mutableStateOf(0),
    val slice: MutableState<Double> = mutableStateOf(0.0)
) {
    fun getPositionInCalculationsEnum(proyarities: Array<Main.PROYARITY>): Int {
        return when(relationship.value) {
            Relationship.NONE -> proyarities[Main.PROYARITY.NONE.ordinal].ordinal
            Relationship.HUSBAND -> proyarities[Main.PROYARITY.HUSABAND.ordinal].ordinal
            Relationship.WIFE -> proyarities[Main.PROYARITY.BROOM.ordinal].ordinal
            Relationship.DAUGHTER -> proyarities[Main.PROYARITY.DOUNTER.ordinal].ordinal
            Relationship.SON -> proyarities[Main.PROYARITY.SON.ordinal].ordinal
            Relationship.GRANDSON -> proyarities[Main.PROYARITY.SON_OF_SON.ordinal].ordinal
            Relationship.GRANDDAUGHTER -> proyarities[Main.PROYARITY.DOUTER_OF_SON.ordinal].ordinal
            Relationship.MOTHER_SISTER -> proyarities[Main.PROYARITY.SIBLINGSMOTHER.ordinal].ordinal // mother sisters / step mother sisters -> brothers from mothers
            Relationship.MOTHER_BROTHER -> proyarities[Main.PROYARITY.SIBLINGSMOTHER.ordinal].ordinal
            Relationship.MOTHER_GRANDMOTHER -> proyarities[Main.PROYARITY.GRANDMOTHER_FORM_MOTHERS_SIDE.ordinal].ordinal
            Relationship.STEPFATHER_SON -> proyarities[Main.PROYARITY.BROTHERS_FROM_FATHER.ordinal].ordinal
            Relationship.STEPFATHER_DAUGHTER -> proyarities[Main.PROYARITY.SISTERS_OF_FATHER.ordinal].ordinal // sisters from another father
            Relationship.FATHER_UNCLE -> proyarities[Main.PROYARITY.UNCLE_OF_FATHERS.ordinal].ordinal // father uncle
            Relationship.FATHER_COUSIN -> proyarities[Main.PROYARITY.SON_OF_FATHER_UNCLE.ordinal].ordinal // father cousin
            Relationship.UNCLE -> proyarities[Main.PROYARITY.UNCLE.ordinal].ordinal // father brother
            Relationship.STEPFATHER_COUSIN_SON -> proyarities[Main.PROYARITY.KIDS_OF_UNCLE_OF_FATHERS.ordinal].ordinal
            Relationship.SISTER -> proyarities[Main.PROYARITY.SISTERS.ordinal].ordinal
            Relationship.BROTHER -> proyarities[Main.PROYARITY.BROTHERS.ordinal].ordinal
            Relationship.BROTHER_SON -> proyarities[Main.PROYARITY.KIDS_OF_BROTHERS.ordinal].ordinal
            Relationship.MOTHER -> proyarities[Main.PROYARITY.MOTHER.ordinal].ordinal
            Relationship.COUSIN -> proyarities[Main.PROYARITY.KIDS_OF_UNCLE.ordinal].ordinal
            Relationship.COUSIN_SON -> proyarities[Main.PROYARITY.KIDS_OF_COUSIN.ordinal].ordinal
            Relationship.FATHER -> proyarities[Main.PROYARITY.FATHER.ordinal].ordinal
            Relationship.GRANDFATHER -> proyarities[Main.PROYARITY.GRANDFATHER.ordinal].ordinal
            Relationship.STEPBROTHER_SON -> proyarities[Main.PROYARITY.KIDS_OF_BROTHERS_OF_FATHER.ordinal].ordinal
            Relationship.FATHER_COUSIN_SON -> proyarities[Main.PROYARITY.KIDS_OF_KIDS_OF_BROTHERS.ordinal].ordinal
            Relationship.STEPBROTHER -> proyarities[Main.PROYARITY.BROTHERS_FROM_FATHER.ordinal].ordinal
            Relationship.FATHER_GRANDMOTHER -> proyarities[Main.PROYARITY.GRANDMOTHER_FORM_FATHER_SIDE.ordinal].ordinal
        }
    }

    constructor(relationship: Relationship) : this(mutableStateOf(relationship))

    val name = relationship.value.name
}

enum class PropertyType {
    CASH, LAND, CAR, COMPANY, GOLD
}

enum class Genders {
    MALE, FEMALE
}


enum class Relationship {
    NONE,
    HUSBAND, WIFE,
    DAUGHTER, SON,
    GRANDSON, GRANDDAUGHTER,
    MOTHER_SISTER, MOTHER_BROTHER, MOTHER_GRANDMOTHER,
    STEPFATHER_DAUGHTER, STEPFATHER_SON, STEPFATHER_COUSIN_SON,
    STEPBROTHER_SON,
    SISTER,
    BROTHER, BROTHER_SON,
    MOTHER,
    UNCLE, COUSIN, COUSIN_SON,
    FATHER, GRANDFATHER, FATHER_COUSIN, FATHER_COUSIN_SON, FATHER_UNCLE, FATHER_GRANDMOTHER,
    STEPBROTHER
}

enum class RelationKey {
    HUSBAND_SIDE,
    WIFE_SIDE,
    DAUGHTER_SIDE,
    SON_SIDE,
    GRANDSON_SIDE,
    GRANDDAUGHTER_SIDE,
    STEPFATHER_SIDE,
    STEPMOTHER_SIDE,
    SISTER_SIDE,
    BROTHER_SIDE,
    MOTHER_SIDE,
    UNCLE_SIDE,
    FATHER_SIDE,
    STEPBROTHER_SIDE
}