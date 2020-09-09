package com.xpert.tkl.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xpert.tkl.R

class SupporDetailtActivity : AppCompatActivity() {
    private var backpress:ImageView?=null
    private  var text:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suppor_detailt)
        backpress = findViewById(R.id.backbtn);
        text = findViewById(R.id.content);

        backpress!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text!!.setText(Html.fromHtml("<p>This document is an electronic record in terms of Information Technology Act, 2000 and rules there under as applicable and the amended provisions pertaining to electronic records in various statutes as amended by the Information Technology Act, 2000. This electronic record is generated by a computer system and does not require any physical or digital signatures. This document is published in accordance with the provisions of Rule 3 (1) of the Information Technology (Intermediaries guidelines) Rules, 2011 that require publishing the rules and regulations, privacy policy and Terms of Use for access or usage of www.drhelpdesk.in website and its mobile applications.</p>\r\n\r\n<p>1. General</p>\r\n\r\n<p>www.drhelpdesk.in, an internet based portal and &ldquo;DrHelpDesk&rdquo; a mobile application, (hereinafter together be referred to as &ldquo;Website&quot;) is operated by Aensa Health Solutions Private Limited (hereinafter to be referred as &ldquo;Company&rdquo; or &ldquo;We&rdquo; or &ldquo;Our&rdquo; or &ldquo;Us&rdquo;), a company duly incorporated under the provisions of the Companies Act, 2013.</p>\r\n\r\n<p>Use of the Website is offered to You, subject to acceptance of all the terms, conditions and notices contained in these Terms including applicable policies which are incorporated herein by reference, along with any amendments / modifications made by Company at its sole discretion and posted on the Website, including by way of imposing an additional charge for access to or use of a service(s). For the purpose of these Terms of Use, wherever the context so requires &quot;You&quot; or &quot;User&quot; shall mean any natural or legal person who has agreed to become a buyer on the Website by providing Account Information (defined below) while registering on the Website as a registered User using the computer systems.</p>\r\n\r\n<p>Company shall not be required to notify You, whether as a registered user or not, of any changes made to the Terms and Conditions (&quot;Terms&quot;). The revised Terms shall be made available on the Website. Your use of the Website and the Services is subject to the most current version of the Terms made available on the Website, at the time of such use. You are requested to regularly visit the Website to view the most current Terms. It shall be your responsibility to check the Terms periodically for changes. Company may require You to provide Your consent to the updated Terms in a specified manner prior to any further use of the Website and the Services, provided on the Website. If no such separate consent is sought, Your continued use of the Website, following changes to the Terms, will constitute your express acceptance of those changes.</p>\r\n\r\n", Html.FROM_HTML_MODE_COMPACT));
        } else {
            text!!.setText(Html.fromHtml("<p>This document is an electronic record in terms of Information Technology Act, 2000 and rules there under as applicable and the amended provisions pertaining to electronic records in various statutes as amended by the Information Technology Act, 2000. This electronic record is generated by a computer system and does not require any physical or digital signatures. This document is published in accordance with the provisions of Rule 3 (1) of the Information Technology (Intermediaries guidelines) Rules, 2011 that require publishing the rules and regulations, privacy policy and Terms of Use for access or usage of www.drhelpdesk.in website and its mobile applications.</p>\\r\\n\\r\\n<p>1. General</p>\\r\\n\\r\\n<p>www.drhelpdesk.in, an internet based portal and &ldquo;DrHelpDesk&rdquo; a mobile application, (hereinafter together be referred to as &ldquo;Website&quot;) is operated by Aensa Health Solutions Private Limited (hereinafter to be referred as &ldquo;Company&rdquo; or &ldquo;We&rdquo; or &ldquo;Our&rdquo; or &ldquo;Us&rdquo;), a company duly incorporated under the provisions of the Companies Act, 2013.</p>\\r\\n\\r\\n<p>Use of the Website is offered to You, subject to acceptance of all the terms, conditions and notices contained in these Terms including applicable policies which are incorporated herein by reference, along with any amendments / modifications made by Company at its sole discretion and posted on the Website, including by way of imposing an additional charge for access to or use of a service(s). For the purpose of these Terms of Use, wherever the context so requires &quot;You&quot; or &quot;User&quot; shall mean any natural or legal person who has agreed to become a buyer on the Website by providing Account Information (defined below) while registering on the Website as a registered User using the computer systems.</p>\\r\\n\\r\\n<p>Company shall not be required to notify You, whether as a registered user or not, of any changes made to the Terms and Conditions (&quot;Terms&quot;). The revised Terms shall be made available on the Website. Your use of the Website and the Services is subject to the most current version of the Terms made available on the Website, at the time of such use. You are requested to regularly visit the Website to view the most current Terms. It shall be your responsibility to check the Terms periodically for changes. Company may require You to provide Your consent to the updated Terms in a specified manner prior to any further use of the Website and the Services, provided on the Website. If no such separate consent is sought, Your continued use of the Website, following changes to the Terms, will constitute your express acceptance of those changes.</p>\\r\\n\\r\\n\""));
        }
    }
}
