import React, {useState} from "react";
import {Formik, Form, Field} from "formik";
import * as Yup from "yup";

const form_headers = {
    "your-project": "Let's craft your product.",
    "meeting-coffee": "We like coffee too! ☕️",
    "birds-bees": "What's on your mind?",
    "video-call": "Let's plan a video call! 🎥",
}

function ContactFeedbackForm(props) {
    let [submitMessage, setSubmitMessage] = useState(false);
    let signInSchema = Yup.object().shape({
        name: Yup.string()
            .required("Name is required")
            .min(3, "Name is too short - should be 3 chars min"),
        email: Yup.string().email().required("Email is required"),
        content: Yup.string()
            .required("Content is required")
            .min(10, "Content is too short - should be 10 chars min")
    });

    let initialValues = {
        name: "",
        email: "",
        content: "",
    };
    if (props.feedBackOption === "your-project") {
        signInSchema = Yup.object().shape({
            name: Yup.string()
                .required("Name is required")
                .min(3, "Name is too short - should be 3 chars min"),
            company: Yup.string()
                .required("Company's Name is required")
                .min(3, "Company's Name is too short - should be 3 chars min"),
            email: Yup.string().email().required("Email is required"),
            budget: Yup.string()
                .required("Budget is required")
                .min(1, "Budget is too short"),
            content: Yup.string()
                .required("Content is required")
                .min(10, "Content is too short - should be 10 chars min")
        });
        initialValues["company"] = "";
        initialValues["budget"] = "";
    }
    console.log(signInSchema);
    return (
        <Formik
            initialValues={initialValues}
            validationSchema={signInSchema}
            onSubmit={(values) => {
                console.log(values);
                setSubmitMessage(true);
                setTimeout(()=>{setSubmitMessage(false)}, 2500)
            }}
        >
            {(formik) => {
                const {errors, touched, isValid, dirty} = formik;
                return (
                    <div className={"feedback-form-screen " + (props.feedBackOption ? "opacity1" : "")}>
                        <Form>
                            <div className="feedback-form-header">
                                <h2 className="overlay-name">{props.feedBackOption ? form_headers[props.feedBackOption] : ""}</h2>
                            </div>
                            <div className="feedback-form-body">
                                {props.feedBackOption === "your-project" ? <div className="feedback-form-left">
                                    <div>
                                        <h4 className="radio-area-header">Timeframe</h4>
                                        <div className="feedback-radio-wrapper">
                                            <ul>
                                                <li className="feedback-radio">
                                                    <label className="strip-top">
                                                        <input type="radio" name="timeframe" value="One month"
                                                               className="white"/>1 month
                                                    </label>
                                                </li>
                                                <li className="feedback-radio">
                                                    <label>
                                                        <input type="radio" name="timeframe" value="Two - Three months"
                                                               className="white"/>2-3 months
                                                    </label>
                                                </li>
                                                <li className="feedback-radio">
                                                    <label>
                                                        <input type="radio" name="timeframe" value="Four or more months"
                                                               className="white"/>4+ months
                                                    </label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div>
                                        <h4 className="checkbox-area-header">Project type</h4>
                                        <div className="feedback-checkbox-wrapper">
                                            <ul>
                                                <li className="feedback-checkbox">
                                                    <label className="strip-top">
                                                        <input type="checkbox" name="desktop" value="Desktop"
                                                               className="white"/>Desktop
                                                    </label>
                                                </li>
                                                <li className="feedback-checkbox">
                                                    <label>
                                                        <input type="checkbox" name="web" value="Web"
                                                               className="white"/>Web
                                                    </label>
                                                </li>
                                                <li className="feedback-checkbox">
                                                    <label>
                                                        <input type="checkbox" name="mobile" value="Mobile"
                                                               className="white"/>Mobile
                                                    </label>
                                                </li>
                                                <li className="feedback-checkbox">
                                                    <label>
                                                        <input type="checkbox" name="other" value="Other"
                                                               className="white"/>Other
                                                    </label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div> : null}
                                <div className="feedback-form-right">
                                    <h4 className="feedback-form-title">Personal details</h4>
                                    <div className="feedback-form-userinfo">
                                        <div className="inputs-div">
                                            <label htmlFor="name"/>
                                            <Field
                                                type="text"
                                                name="name"
                                                id="name"
                                                className={
                                                    errors.name && touched.name ? "error" : null
                                                }
                                                placeholder={errors.name && touched.name ? errors.name : "Name"}
                                            />

                                            {props.feedBackOption === "your-project" ? <><label htmlFor="name"/>
                                                <Field
                                                    type="text"
                                                    name="company"
                                                    id="company"
                                                    className={
                                                        errors.company && touched.company ? "error" : null
                                                    }
                                                    placeholder={errors.company && touched.company ? errors.company : "Company"}
                                                /></> : null}

                                            <label htmlFor="email"/>
                                            <Field
                                                type="email"
                                                name="email"
                                                id="email"
                                                className={
                                                    errors.email && touched.email ? "error" : null
                                                }
                                                placeholder={errors.email && touched.email ? errors.email : "Email"}
                                            />

                                            {props.feedBackOption === "your-project" ? <><label htmlFor="name"/>
                                                <Field
                                                    type="text"
                                                    name="budget"
                                                    id="budget"
                                                    className={
                                                        errors.budget && touched.budget ? "error" : null
                                                    }
                                                    placeholder={errors.budget && touched.budget ? errors.budget : "Budget"}
                                                /></> : null}

                                        </div>
                                        <div className="textarea-div">
                                            <Field
                                                as="textarea"
                                                name="content"
                                                id="content"
                                                className={
                                                    errors.content && touched.content ? "error" : null
                                                }
                                                placeholder={errors.content && touched.content ? errors.content : "What do you want to talk about?"}
                                            />
                                        </div>
                                    </div>
                                    <button
                                        type="submit"
                                        className={"next btn btn-primary btn-white " + (!(dirty && isValid) ? "disabled-btn" : "")}
                                    >
                                        Send Inquiry
                                    </button>
                                    <span className={"submit-message " + (submitMessage ? "show" : "")}>Details have been captured successfully!</span>
                                </div>
                            </div>
                        </Form>
                    </div>
                );
            }}
        </Formik>
    );
}

export default ContactFeedbackForm;
