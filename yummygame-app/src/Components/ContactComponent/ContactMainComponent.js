import React, {} from "react";
import ContactInformationComponent from "./ContactInformationComponent";
import ContactFeedbackComponent from "./ContactFeedbackComponent";

function ContactMainComponent() {
    return (
        <>
            <div className="contact-modal overlay">
                <button className="close-btn"/>
                <div className="contact-mother">
                    <ContactInformationComponent/>
                    <ContactFeedbackComponent/>
                </div>
            </div>
        </>
    );
}

export default ContactMainComponent;
