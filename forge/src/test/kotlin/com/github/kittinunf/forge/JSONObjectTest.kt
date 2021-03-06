package com.github.kittinunf.forge

import com.github.kittinunf.forge.core.DeserializedResult
import com.github.kittinunf.forge.core.JSON
import com.github.kittinunf.forge.core.PropertyNotFoundException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.json.JSONObject
import org.junit.Test

class JSONObjectTest : BaseTest() {

    @Test
    fun testParseJSON1() {
        val j = JSONObject("{ \"hello\" : \"world\" }")
        val json = JSON.parse(j)
        assertThat(json.value, notNullValue())
    }

    @Test
    fun testParseJSON2() {
        val j = JSONObject(userJson)
        val json = JSON.parse(j)
        assertThat(json.value, notNullValue())
    }

    @Test
    fun testJSONValidValue() {
        val json = JSON.parse((JSONObject(userJson)))

        val id = json.find("id")?.valueAs<Int>()
        assertThat(id, notNullValue())
        assertThat(id!!.get<Int>(), equalTo(1))

        val name = json.find("name")?.valueAs<String>()

        assertThat(name, notNullValue())
        assertThat(name!!.get<String>(), equalTo("Clementina DuBuque"))

        val isDeleted = json.find("is_deleted")?.valueAs<Boolean>()

        assertThat(isDeleted, notNullValue())
        assertThat(isDeleted!!.get<Boolean>(), equalTo(true))

        val addressStreet = json.find("address.street")?.valueAs<String>()

        assertThat(addressStreet, notNullValue())
        assertThat(addressStreet!!.get<String>(), equalTo("Kattie Turnpike"))


        val addressGeoLat = json.find("address.geo.lat")?.valueAs<Double>()

        assertThat(addressGeoLat, notNullValue())
        assertThat(addressGeoLat!!.get<Double>(), equalTo(-38.2386))
    }

    @Test
    fun testJSONInvalidValue() {
        val json = JSON.parse((JSONObject(userJson)))

        val notFoundName = json.find("n")?.valueAs<String>() ?:
                DeserializedResult.Failure(PropertyNotFoundException("n"))

        assertThat(notFoundName, notNullValue())
        assertThat(notFoundName.get<Exception>(), instanceOf(PropertyNotFoundException::class.java))

        val notFoundAddressSt = json.find("address.st")?.valueAs<String>() ?:
                DeserializedResult.Failure(PropertyNotFoundException("address.st"))

        assertThat(notFoundAddressSt, notNullValue())
        assertThat(notFoundAddressSt.get<Exception>(), instanceOf(PropertyNotFoundException::class.java))
    }

}