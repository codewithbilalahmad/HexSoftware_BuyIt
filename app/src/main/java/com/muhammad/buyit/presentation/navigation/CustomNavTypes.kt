package com.muhammad.buyit.presentation.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.muhammad.data.model.DataProductModel
import com.muhammad.data.model.request.AddressDataModel
import kotlinx.serialization.json.Json

object CustomNavType{
    val Product = object : NavType<DataProductModel>(isNullableAllowed = false) {
        override fun put(
            bundle: SavedState,
            key: String,
            value: DataProductModel,
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: DataProductModel): String {
            return Uri.encode(Json.encodeToString(value))
        }
        override fun get(
            bundle: SavedState,
            key: String,
        ): DataProductModel? {
           return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): DataProductModel {
            return Json.decodeFromString(Uri.decode(value))
        }
    }
    val Address = object : NavType<AddressDataModel>(isNullableAllowed = false) {
        override fun put(
            bundle: SavedState,
            key: String,
            value: AddressDataModel,
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: AddressDataModel): String {
            return Uri.encode(Json.encodeToString(value))
        }
        override fun get(
            bundle: SavedState,
            key: String,
        ): AddressDataModel? {
           return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): AddressDataModel {
            return Json.decodeFromString(Uri.decode(value))
        }
    }
}