import java.util.Scanner

class Main {
    enum class PROYARITY {
        NONE, SON, SON_OF_SON, SON_OF_SON_OF_SON, FATHER, GRANDFATHER, BROTHERS,
        BROTHERS_FROM_FATHER, KIDS_OF_BROTHERS, KIDS_OF_BROTHERS_OF_FATHER,
        KIDS_OF_KIDS_OF_BROTHERS, KIDS_OF_KIDS_OF_BROTHERS_OF_FATHER,
        UNCLE, UNCLE_OF_FATHERS, KIDS_OF_UNCLE, KIDS_OF_UNCLE_OF_FATHERS, KIDS_OF_COUSIN, KIDS_OF_COUSIN_OF_FATHER,
        FATHER_UNCLE, FATHER_UNCLE_OF_FATHER, SON_OF_FATHER_UNCLE, SON_OF_FATHER_UNCLE_OF_FATHER,  /**/
        SISTERS, DOUNTER, DOUTER_OF_SON, DOUTER_OF_SON_OF_SON,
        HUSABAND, BROOM, SISTERS_OF_FATHER, SIBLINGSMOTHER, BROTHERS_FROM_MOTHER, GRANDMOTHER_FORM_MOTHERS_SIDE, MOTHER, GRANDMOTHER_FORM_FATHER_SIDE
    }

    private val proyarities = PROYARITY.values()

    //Ask as above
    private var Inheritor: IntArray = IntArray(proyarities.size)
    private val Inheritor_wealth = arrayOfNulls<String>(proyarities.size)
    private val Blocked = ArrayList<String>()
    private var proyarity: PROYARITY? = null
    private var total_wealth = 0.0
    private var rem = 0.0
    private var Inheritor_branch = false
    private var siblings_counter = 0
    private var total_share = 0.0

    fun provideData(
        Inheritor: IntArray,
        totalWealth: Double,
        rem: Double = totalWealth,
        totalShare: Double,
    ) {
        this.Inheritor = Inheritor
        this.total_wealth = totalWealth
        this.rem = rem
        this.total_share = totalShare

        this.proyarity = PROYARITY.NONE

        var i = 1
        while (i <= 22) {
            if (Inheritor[i] != 0) {
                if (proyarity == PROYARITY.NONE)
                    proyarity = proyarities[i];
            }
            i++;
        }
    }

//    fun read_data() {
//        val scan = Scanner(System.`in`)
//        println("Total Wealth:  ")
//        total_wealth = scan.nextDouble()
//        rem = total_wealth
//        total_share = 0.0
//        proyarity = PROYARITY.NONE
//        val i = 1
//        Inheritor[proyarities[PROYARITY.MOTHER.ordinal].ordinal] = 1
//        Inheritor[proyarities[PROYARITY.FATHER.ordinal].ordinal] = 1
//        Inheritor[proyarities[PROYARITY.SON.ordinal].ordinal] = 5
//        Inheritor[proyarities[PROYARITY.DOUNTER.ordinal].ordinal] = 2
//        Inheritor[proyarities[PROYARITY.SISTERS.ordinal].ordinal] = 1
//        Inheritor[proyarities[PROYARITY.BROTHERS_FROM_MOTHER.ordinal].ordinal] = 1
//        proyarity = PROYARITY.SON
//
////		while(i<=25) {
////			Inheritor[i]=scan.nextInt();
////			if(Inheritor[i]!=0) {
////				if(proyarity == PROYARITY.NONE)
////					proyarity = proyarities[i];
////			}
////			i++;
////		}
//        process_data()
//    }

    fun process_data() {
        val father = proyarities[PROYARITY.FATHER.ordinal]
        if (proyarity != PROYARITY.NONE) total_share = -1.0
        when (proyarity) {
            PROYARITY.SON, PROYARITY.SON_OF_SON, PROYARITY.SON_OF_SON_OF_SON -> {
                Inheritor_branch = true
                // affects the father, mother and spowas
                val index = proyarities[proyarity!!.ordinal].ordinal
                Inheritor_wealth[proyarity!!.ordinal] = "rem"
                if (Inheritor[father.ordinal] != 0) {
                    Inheritor_wealth[proyarities[father.ordinal].ordinal] = "0.166667"
                }
                if (Inheritor[proyarities[PROYARITY.GRANDFATHER.ordinal].ordinal] != 0) {
                    Inheritor_wealth[proyarities[PROYARITY.GRANDFATHER.ordinal].ordinal] =
                        "0.166667"
                }
                if (Inheritor[proyarities[PROYARITY.HUSABAND.ordinal].ordinal] != 0) {
                    Inheritor_wealth[proyarities[PROYARITY.HUSABAND.ordinal].ordinal] = "0.25"
                }
                total_share = -1.0
            }

            PROYARITY.FATHER, PROYARITY.GRANDFATHER -> {
                if (!Inheritor_branch) Inheritor_wealth[proyarities[proyarity!!.ordinal].ordinal] =
                    "rem" else {
                    if (Inheritor[proyarities[PROYARITY.SON.ordinal].ordinal] != 0) Inheritor_wealth[proyarities[proyarity!!.ordinal].ordinal] =
                        "0.166667"
                    if (Inheritor[proyarities[PROYARITY.DOUNTER.ordinal].ordinal] != 0) Inheritor_wealth[proyarities[proyarity!!.ordinal].ordinal] =
                        "0.166667,rem" //,rem
                }
                total_share = -1.0
            }

            PROYARITY.BROTHERS -> {
                siblings_counter += Inheritor[proyarities[PROYARITY.BROTHERS_FROM_FATHER.ordinal].ordinal]
                //if(Inheritor[proyarities[(PROYARITY.SISTERS).ordinal()].ordinal()]!=0)
                Inheritor_wealth[proyarities[PROYARITY.BROTHERS.ordinal].ordinal] = "rem" //,SISTERS
                total_share = -1.0
            }

            PROYARITY.BROTHERS_FROM_FATHER -> {
                siblings_counter += Inheritor[proyarities[PROYARITY.BROTHERS_FROM_FATHER.ordinal].ordinal]
                //if(Inheritor[proyarities[(PROYARITY.SISTERS).ordinal()].ordinal()]!=0)
                Inheritor_wealth[proyarities[PROYARITY.BROTHERS_FROM_FATHER.ordinal].ordinal] =
                    "rem"
                total_share = -1.0
            }

            PROYARITY.KIDS_OF_BROTHERS -> {
                Inheritor_wealth[proyarities[PROYARITY.KIDS_OF_BROTHERS.ordinal].ordinal] = "rem"
                total_share = -1.0
            }

            PROYARITY.KIDS_OF_BROTHERS_OF_FATHER -> {
                Inheritor_wealth[proyarities[PROYARITY.KIDS_OF_BROTHERS_OF_FATHER.ordinal].ordinal] =
                    "rem"
                total_share = -1.0
            }

            PROYARITY.UNCLE -> {
                Inheritor_wealth[proyarities[PROYARITY.UNCLE.ordinal].ordinal] = "rem"
                total_share = -1.0
            }

            PROYARITY.UNCLE_OF_FATHERS -> Inheritor_wealth[proyarities[PROYARITY.UNCLE_OF_FATHERS.ordinal].ordinal] =
                "rem"

            PROYARITY.KIDS_OF_UNCLE -> Inheritor_wealth[proyarities[PROYARITY.KIDS_OF_UNCLE.ordinal].ordinal] =
                "rem"

            PROYARITY.KIDS_OF_UNCLE_OF_FATHERS -> Inheritor_wealth[proyarities[PROYARITY.KIDS_OF_UNCLE_OF_FATHERS.ordinal].ordinal] =
                "rem"

            PROYARITY.FATHER_UNCLE -> Inheritor_wealth[proyarities[PROYARITY.FATHER_UNCLE.ordinal].ordinal] =
                "rem"

            PROYARITY.SON_OF_FATHER_UNCLE -> Inheritor_wealth[proyarities[PROYARITY.SON_OF_FATHER_UNCLE.ordinal].ordinal] =
                "rem"

            PROYARITY.SON_OF_FATHER_UNCLE_OF_FATHER -> {
                Inheritor_wealth[proyarities[PROYARITY.SON_OF_FATHER_UNCLE_OF_FATHER.ordinal].ordinal] =
                    "rem"
                total_share = -1.0
            }

            else -> {}
        }


//		int inhi = proyarities[proyarity.ordinal()].ordinal();
//		System.out.print(Inheritor[inhi]+ " " + proyarity.toString() + " Takes the remaning: ");
//		System.out.print(rem);
//		System.out.println(" each one takes: "+ (double)rem/Inheritor[inhi]);
        val index = proyarity!!.ordinal
        if (index != 0) {
            for (i in index + 1 until proyarities[PROYARITY.SON_OF_FATHER_UNCLE_OF_FATHER.ordinal].ordinal) {
                if (Inheritor[i] != 0) {
                    Blocked.add(proyarities[i].name)
                }
            }
        }
        process_first_class()
    }

    private fun process_first_class() {
        val start_index = proyarities[PROYARITY.SISTERS.ordinal].ordinal
        for (i in start_index until proyarities.size) {
            try {
                if (Inheritor[i] != 0) {
                    if (proyarities[i].toString() === "DOUNTER") {
                        Inheritor_branch = true
                    }
                    val classObj: Class<*> = this.javaClass
                    val printMessage = classObj.getDeclaredMethod(
                        proyarities[i].toString(),
                        Int::class.java
                    )
                    printMessage.invoke(this, i)
                    try {
                        if (total_share != -1.0) total_share += Inheritor_wealth[i]!!.toDouble()
                    } catch (e: Exception) {
                        total_share = -1.0
                    }
                }
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: SecurityException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        calculate_wealth()
    }

    private fun calculate_wealth() {
        var part = 1.0
        if (total_share != -1.0) {
            val response = total_wealth * total_share
            part = total_wealth / response
        }
        var rem = total_wealth
        var marge = 0
        for (i in Inheritor_wealth.indices.reversed()) {
            if (Inheritor_wealth[i] != null) {
                print(proyarities[i].toString() + " " + Inheritor[i] + " " + Inheritor_wealth[i] + "--->")
                val split =
                    Inheritor_wealth[i]!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                if (split.size > 1) {
                    try {
                        val result = total_wealth * split[0].toDouble() * part
                        rem -= result
                        println("$result+ rem $rem")
                    } catch (e: Exception) {
                        when (split[0]) {
                            "SON" -> {
                                Inheritor_wealth[1] = null
                                marge = 1
                            }

                            "SON_OF_SON" -> {
                                Inheritor_wealth[2] = null
                                marge = 2
                            }

                            "SON_OF_SON_OF_SON" -> {
                                Inheritor_wealth[3] = null
                                marge = 3
                            }

                            "BROTHERS" -> {
                                Inheritor_wealth[6] = null
                                marge = 6
                            }
                        }
                        val girls = rem / 4
                        val boys = rem / 2
                        print(girls / Inheritor[i] * part)
                        println(" " + proyarities[marge].toString() + " " + boys + " for each one " + boys / Inheritor[marge] * part)
                    }
                } else {
                    if (Inheritor_wealth[i] !== "rem") {
                        val result = total_wealth * Inheritor_wealth[i]!!.toDouble() * part
                        rem -= result
                        println(result)
                    } else println(rem)
                }
            }
        }
        val index = proyarity!!.ordinal
        println("المحجوبون بفعل $proyarity")
        for (i in Blocked.indices) {
            println(Blocked[i])
        }
    }

    private fun HUSABAND(index: Int) {
        if (!Inheritor_branch) {
            Inheritor_wealth[index] = "0.5"
        } else {
            Inheritor_wealth[index] = "0.25"
        }
    }

    private fun BROOM(index: Int) {
        if (!Inheritor_branch) {
            Inheritor_wealth[index] = "0.25"
        } else {
            Inheritor_wealth[index] = "0.125" //,e
        }
    }

    private fun MOTHER(index: Int) {
        if (Inheritor_branch || siblings_counter >= 2) {
            Inheritor_wealth[index] = "0.166667"
        } else if (!Inheritor_branch && siblings_counter < 2) {
            Inheritor_wealth[index] = "0.33333"
        } else if (Inheritor[proyarities[PROYARITY.BROOM.ordinal].ordinal] != 0 ||
            Inheritor[proyarities[PROYARITY.HUSABAND.ordinal].ordinal] != 0
        ) {
            Inheritor_wealth[index] = "0.33333,rem"
        }
    }

    private fun SIBLINGSMOTHER(index: Int) {
        if (Inheritor[proyarities[PROYARITY.FATHER.ordinal].ordinal] == 0
            && Inheritor[proyarities[PROYARITY.SON.ordinal].ordinal] == 0
        ) {
            if (Inheritor[index] == 1) Inheritor_wealth[index] =
                "0.166667" else Inheritor_wealth[index] = "0.33333" //,e
        } else {
            Blocked.add("Mother's Siblings")
        }
    }

    private fun GRANDMOTHER_FORM_MOTHERS_SIDE(index: Int) {
        if (Inheritor[proyarities[PROYARITY.MOTHER.ordinal].ordinal] == 0) {
            Inheritor_wealth[index] = "0.166667"
        } else {
            Blocked.add("GRANDMOTHER_FORM_MOTHERS_SIDE")
        }
    }

    private fun GRANDMOTHER_FORM_FATHER_SIDE(index: Int) { //ام لاب و ام لاب اب
        if (Inheritor[proyarities[PROYARITY.MOTHER.ordinal].ordinal] == 0 && Inheritor[proyarities[PROYARITY.FATHER.ordinal].ordinal] == 0) {
            Inheritor_wealth[index] = "0.166667"
        } else {
            Blocked.add("GRANDMOTHER_FORM_FATHER_SIDE")
        }
    }

    private fun DOUNTER(index: Int) {
        Inheritor_branch = true
        if (Inheritor[proyarities[PROYARITY.SON.ordinal].ordinal] != 0) {
            Inheritor_wealth[index] = "SON,rem"
        } else if (Inheritor[index] == 1) {
            Inheritor_wealth[index] = "0.5"
        } else if (Inheritor[index] > 1) { //
            Inheritor_wealth[index] = "0.666667"
        }
    }

    private fun DOUTER_OF_SON(index: Int) {
        Inheritor_branch = true
        if (Inheritor[proyarities[PROYARITY.SON.ordinal].ordinal] == 0 &&
            Inheritor[index - 1] <= 1
        ) {
            if (Inheritor[proyarities[PROYARITY.SON_OF_SON.ordinal].ordinal] != 0) {
                Inheritor_wealth[index] = "SON_OF_SON,rem"
            } else if (Inheritor[index - 1] == 1) {
                Inheritor_wealth[index] = "0.166667" //,e;
            } else if (Inheritor[index - 1] == 0) {
                Inheritor_wealth[index] = "0.666667"
            } else if (Inheritor[index] == 1) {
                Inheritor_wealth[index] = "0.5"
            }
        } else {
            Blocked.add("DOUTER_OF_SON")
        }
    }

    private fun DOUTER_OF_SON_OF_SON(index: Int) {
        Inheritor_branch = true
        if (Inheritor[proyarities[PROYARITY.SON_OF_SON.ordinal].ordinal] == 0 &&
            Inheritor[index - 1] <= 1
        ) {
            if (Inheritor[proyarities[PROYARITY.SON_OF_SON_OF_SON.ordinal].ordinal] != 0) {
                Inheritor_wealth[index] = "SON_OF_SON_OF_SON,rem"
            } else if (Inheritor[index - 1] == 1) {
                Inheritor_wealth[index] = "0.166667" //,e;
            } else if (Inheritor[index - 1] == 0) {
                Inheritor_wealth[index] = "0.666667"
            } else if (Inheritor[index] == 1) {
                Inheritor_wealth[index] = "0.5"
            }
        } else {
            Blocked.add("DOUTER_OF_SON_OF_SON")
        }
    }

    private fun SISTERS(index: Int) {
        if (Inheritor[proyarities[PROYARITY.BROTHERS.ordinal].ordinal] != 0) {
            Inheritor_wealth[index] = "BROTHERS,rem"
            return
        } else {
            if (!Inheritor_branch) {
                if (Inheritor[index] == 1) {
                    Inheritor_wealth[index] = "0.5"
                } else if (Inheritor[index] >= 2) {
                    Inheritor_wealth[index] = "0.666667" //,e
                }
                return
            }
            if (Inheritor[proyarities[PROYARITY.DOUNTER.ordinal].ordinal] == 1) {
                Inheritor_wealth[index] = "rem" //,e
                return
            }
        }
        Blocked.add("SISTERS")
    }

    private fun SISTERS_OF_FATHER(index: Int) {
        if (Inheritor[proyarities[PROYARITY.BROTHERS_FROM_FATHER.ordinal].ordinal] != 0) Inheritor_wealth[index] =
            "BROTHERS_FROM_FATHER,rem" else {
            if (!Inheritor_branch &&
                Inheritor[index - 1] <= 1
            ) {
                if (Inheritor[index - 1] == 1) {
                    Inheritor_wealth[index] = "0.166667"
                } else if (Inheritor[index] == 1) Inheritor_wealth[index] =
                    "0.5" else if (Inheritor[index] >= 2) {
                    Inheritor_wealth[index] = "0.666667" //,e
                }
            } else {
                Blocked.add("SISTERS_OF_FATHER")
            }
        }
    }

    private fun BROTHERS_FROM_MOTHER(index: Int) {
        siblings_counter += Inheritor[index]
        if (Inheritor[proyarities[PROYARITY.SON.ordinal].ordinal] == 0 && Inheritor[proyarities[PROYARITY.FATHER.ordinal].ordinal] == 0 && !Inheritor_branch) { //
            if (Inheritor[index] == 1) {
                Inheritor_wealth[index] = "0.166667"
            } else if (Inheritor[index] >= 2) Inheritor_wealth[index] = "0.333333" //,e
        } else {
            Blocked.add("BROTHERS_FROM_MOTHER")
        }
    }
}