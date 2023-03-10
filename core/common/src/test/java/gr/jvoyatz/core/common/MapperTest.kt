package gr.jvoyatz.core.common

import com.google.common.truth.Truth
import gr.jvoyatz.core.common.StringToIntMapper.mapFromDomainModel
import gr.jvoyatz.core.common.StringToIntMapper.mapToDomainModel
import org.junit.Test

class MapperTest {

    @Test
    fun `map string item to int type`(){
        //given
        val list = listOf<String>("1", "2", "3")


        //when
        val domainModel = "1".mapToDomainModel()

        //then
        Truth.assertThat(domainModel.toString()).isEqualTo("1")

        //when
        val model = 1.mapFromDomainModel()
        //then
        Truth.assertThat(model).isEqualTo(1.toString())
    }
    @Test
    fun `map list items to another type with success`(){
        //given
        val list = listOf<String>("1", "2", "3")


        //when
        val mapList = list.mapList {
            it.mapToDomainModel()
        }

        //then
        list.zip(mapList) { a, b ->
            Truth.assertThat(a).isEqualTo(b.toString())
        }
    }
}
