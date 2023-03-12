package gr.jvoyatz.sportspot.core.common

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.core.common.StringToIntDtoMapper.domainToDto
import gr.jvoyatz.sportspot.core.common.StringToIntDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.core.common.StringToIntEntityMapper.domainToEntity
import gr.jvoyatz.sportspot.core.common.StringToIntEntityMapper.entityToDomain
import org.junit.Test

class MapperTest {

    @Test
    fun `map dto string item to int type`(){
        //given
        val list = listOf<String>("1", "2", "3")


        //when
        val domainModel = "1".dtoToDomain()

        //then
        Truth.assertThat(domainModel.toString()).isEqualTo("1")

        //when
        val model = 1.domainToDto()
        //then
        Truth.assertThat(model).isEqualTo(1.toString())
    }
    @Test
    fun `map dto list items to another type with success`(){
        //given
        val list = listOf<String>("1", "2", "3")


        //when
        val mapList = list.mapList {
            it.dtoToDomain()
        }

        //then
        list.zip(mapList) { a, b ->
            Truth.assertThat(a).isEqualTo(b.toString())
        }
    }

    @Test
    fun `map entity string item to int type`(){
        //given
        val list = listOf<String>("1", "2", "3")


        //when
        val domainModel = "1".entityToDomain()

        //then
        Truth.assertThat(domainModel.toString()).isEqualTo("1")

        //when
        val model = 1.domainToEntity()
        //then
        Truth.assertThat(model).isEqualTo(1.toString())
    }
    @Test
    fun `map entity list items to another type with success`(){
        //given
        val list = listOf<String>("1", "2", "3")


        //when
        val mapList = list.mapList {
            it.entityToDomain()
        }

        //then
        list.zip(mapList) { a, b ->
            Truth.assertThat(a).isEqualTo(b.toString())
        }
    }
}
