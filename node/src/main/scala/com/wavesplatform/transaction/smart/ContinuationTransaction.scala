package com.wavesplatform.transaction.smart

import cats.implicits._
import com.wavesplatform.account.{AddressScheme, PrivateKey, PublicKey}
import com.wavesplatform.common.state.ByteStr
import com.wavesplatform.crypto
import com.wavesplatform.lang.v1.compiler.Terms.EXPR
import com.wavesplatform.transaction.Asset.Waves
import com.wavesplatform.transaction._
import com.wavesplatform.transaction.validation.TxValidator
import monix.eval.Coeval
import play.api.libs.json.JsObject

import scala.util.Try

case class ContinuationTransaction(
    expr: EXPR,
    invokeScriptTransactionId: ByteStr,
    timestamp: TxTimestamp,
    sender: PublicKey,
    proofs: Proofs
) extends Transaction
    with VersionedTransaction
    with FastHashId
    with TxWithFee.InCustomAsset {

  override val builder: TransactionParser     = ContinuationTransaction
  override val bytes: Coeval[Array[Byte]]     = Coeval(Array())
  override val json: Coeval[JsObject]         = Coeval(JsObject(Seq()))
  override val bodyBytes: Coeval[Array[Byte]] = Coeval(Array())

  override def chainId: Byte = AddressScheme.current.chainId

  override def feeAssetId: Asset = Waves

  override def fee: TxAmount = 0

  override def version: TxVersion = TxVersion.V1
}

object ContinuationTransaction extends TransactionParser {
  override type TransactionT = ContinuationTransaction

  override def typeId: TxType = 18: Byte

  override def supportedVersions: Set[TxVersion] = Set(1)

  override def parseBytes(bytes: Array[Byte]): Try[ContinuationTransaction] = ???

  override implicit def validator: TxValidator[ContinuationTransaction] = _.validNel
}
