package com.muhammad.data.di

import com.muhammad.data.network.NetworkServiceImp
import com.muhammad.data.network.client.HttpClientFactory
import com.muhammad.data.repository.CartRepositoryImp
import com.muhammad.data.repository.CategoryRepositoryImp
import com.muhammad.data.repository.OrderRepositoryImp
import com.muhammad.data.repository.ProductRepositoryImp
import com.muhammad.data.repository.UserRepositoryImp
import com.muhammad.domain.network.NetworkService
import com.muhammad.domain.repository.CartRepository
import com.muhammad.domain.repository.CategoryRepository
import com.muhammad.domain.repository.OrderRepository
import com.muhammad.domain.repository.ProductRepository
import com.muhammad.domain.repository.UserRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { HttpClientFactory.createClient() }
    single { NetworkServiceImp(get()) }.bind<NetworkService>()
    single { CartRepositoryImp(get()) }.bind<CartRepository>()
    single { CategoryRepositoryImp(get()) }.bind<CategoryRepository>()
    single { OrderRepositoryImp(get()) }.bind<OrderRepository>()
    single { ProductRepositoryImp(get()) }.bind<ProductRepository>()
    single { UserRepositoryImp(get()) }.bind<UserRepository>()
}