import React, {useState} from "react";
import ContactInformationComponent from "./ContactInformationComponent";
import ContactFeedbackComponent from "./ContactFeedbackComponent";

function ContactMainComponent() {
    const [feedBackOption, setFeedBackOption] = useState(null);
    return (
        <>
            <div className="contact-modal overlay">
                <button className="close-btn"/>
                <div className="contact-mother">
                    <ContactInformationComponent feedBackOption={feedBackOption}/>
                    <ContactFeedbackComponent feedBackOption={feedBackOption} setFeedBackOption={setFeedBackOption}/>
                </div>
            </div>
        </>
    );
}

export default ContactMainComponent;
