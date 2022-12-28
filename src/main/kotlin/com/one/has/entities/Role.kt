package com.one.has.entities

import jakarta.persistence.*

@Entity
@Table(name = "role", schema = "public")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "name")
    var name: String = ""

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "permission_id")
    var permissions: Permissions = Permissions()
}