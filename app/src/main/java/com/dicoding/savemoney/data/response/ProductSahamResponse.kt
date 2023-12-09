package com.dicoding.savemoney.data.response

import com.google.gson.annotations.*

data class ProductSahamResponse(

    @field:SerializedName("data")
    val data: DataProfile? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class AuditCommitteeItem(

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("profile_id")
    val profileId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)

data class SecretaryItem(

    @field:SerializedName("profile_id")
    val profileId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)

data class ShareholdersItem(

    @field:SerializedName("amount")
    val amount: String? = null,

    @field:SerializedName("profile_id")
    val profileId: Int? = null,

    @field:SerializedName("percentage")
    val percentage: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("holding_type")
    val holdingType: String? = null
)

data class DataProfile(

    @field:SerializedName("symbol")
    val symbol: String? = null,

    @field:SerializedName("sub_sector_name")
    val subSectorName: String? = null,

    @field:SerializedName("directors")
    val directors: List<DirectorsItem?>? = null,

    @field:SerializedName("about")
    val about: String? = null,

    @field:SerializedName("ipo_founders_shares")
    val ipoFoundersShares: Long? = null,

    @field:SerializedName("ipo_fund_raised")
    val ipoFundRaised: Long? = null,

    @field:SerializedName("ipo_securities_administration_bureau")
    val ipoSecuritiesAdministrationBureau: String? = null,

    @field:SerializedName("ipo_listing_date")
    val ipoListingDate: String? = null,

    @field:SerializedName("secretary")
    val secretary: List<SecretaryItem?>? = null,

    @field:SerializedName("audit_committee")
    val auditCommittee: List<AuditCommitteeItem?>? = null,

    @field:SerializedName("subsidiary_companies")
    val subsidiaryCompanies: List<SubsidiaryCompaniesItem?>? = null,

    @field:SerializedName("ipo_total_listed_shares")
    val ipoTotalListedShares: Long? = null,

    @field:SerializedName("logo")
    val logo: String? = null,

    @field:SerializedName("fax")
    val fax: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("ipo_offering_shares")
    val ipoOfferingShares: Int? = null,

    @field:SerializedName("website")
    val website: String? = null,

    @field:SerializedName("industry_name")
    val industryName: String? = null,

    @field:SerializedName("commissioners")
    val commissioners: List<CommissionersItem?>? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("shareholders")
    val shareholders: List<ShareholdersItem?>? = null,

    @field:SerializedName("npwp")
    val npwp: String? = null,

    @field:SerializedName("ipo_percentage")
    val ipoPercentage: Any? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("sub_industry_name")
    val subIndustryName: String? = null,

    @field:SerializedName("sector_name")
    val sectorName: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DirectorsItem(

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("profile_id")
    val profileId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("affiliated")
    val affiliated: String? = null
)

data class CommissionersItem(

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("independent")
    val independent: String? = null,

    @field:SerializedName("profile_id")
    val profileId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)

data class SubsidiaryCompaniesItem(

    @field:SerializedName("total_asset")
    val totalAsset: String? = null,

    @field:SerializedName("profile_id")
    val profileId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("percentage_own")
    val percentageOwn: String? = null,

    @field:SerializedName("sector")
    val sector: String? = null
)
