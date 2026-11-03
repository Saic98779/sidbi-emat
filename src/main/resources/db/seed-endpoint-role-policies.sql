INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseRecommendationRead', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'View BSE recommendations', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseRecommendationWrite', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO', 'Create/update vendor disbursements and BSE recommendations', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseRecommendationCreate', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO', 'Create BSE recommendation', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseRecommendationUpdate', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO', 'Update BSE recommendation', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseRecommendationHo', 'SIDBI_HO_MAKER,SIDBI_HO_CHECKER,SIDBI_RO,GT_PMU', 'Head office level BSE recommendation actions', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationRead', 'GT_FIELD_TEAM,GT_PMU,BSE,MANPOWER_AGENCY,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'View industry association registrations', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationWrite', 'GT_FIELD_TEAM,BSE,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO,SIDBI_SDE,SIDBI_HO_CHECKER', 'Create/update industry association registrations', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('sidbiSde', 'SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER', 'SIDBI SDE approve/delete registration actions', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('usersRead', 'SIDBI_HO_MAKER,SIDBI_HO_CHECKER,SIDBI_RO', 'View users', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseSalaryRead', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'View BSE salary records', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseSalaryApprovedIndustryAssociationsRead', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'View approved industry association names for BSE salary', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseSalaryCreate', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO', 'Create BSE salary record', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseSalaryUpdate', 'BSE,GT_FIELD_TEAM,GT_PMU,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO', 'Update BSE salary record', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bseSalaryDelete', 'SIDBI_HO_MAKER,SIDBI_HO_CHECKER,SIDBI_RO', 'Delete BSE salary record', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationAppraisalRead', 'GT_FIELD_TEAM,GT_PMU,BSE,MANPOWER_AGENCY,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'View industry association appraisals', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationAppraisalSearch', 'GT_FIELD_TEAM,GT_PMU,BSE,MANPOWER_AGENCY,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'Search industry association appraisals', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationAppraisalCreate', 'GT_FIELD_TEAM,BSE,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO,SIDBI_SDE,SIDBI_HO_CHECKER', 'Create industry association appraisal', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationAppraisalUpdate', 'GT_FIELD_TEAM,BSE,MANPOWER_AGENCY,SIDBI_HO_MAKER,SIDBI_RO,SIDBI_SDE,SIDBI_HO_CHECKER', 'Update industry association appraisal', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationAppraisalApprove', 'SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER', 'Approve industry association appraisal (SIDBI)', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationAppraisalDelete', 'SIDBI_HO_MAKER,SIDBI_HO_CHECKER,SIDBI_RO', 'Delete industry association appraisal', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('piiEncryptionKeyRead', 'GT_FIELD_TEAM,GT_PMU,BSE,MANPOWER_AGENCY,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'Read PII encryption key', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Disbursement Note Capacity Building IA', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Disbursement Note Capacity Building IA', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Disbursement Note Capacity Building IA', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Disbursement Note Capacity Building IA', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaOfficialsCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Disbursement Note Capacity Building IA Officials', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaOfficialsRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Disbursement Note Capacity Building IA Officials', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaOfficialsUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Disbursement Note Capacity Building IA Officials', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('disbursementNoteCapacityBuildingIaOfficialsDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Disbursement Note Capacity Building IA Officials', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('dia3CInfoSeriesCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create DIA 3C Info Series', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('dia3CInfoSeriesRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View DIA 3C Info Series', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('dia3CInfoSeriesUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update DIA 3C Info Series', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('dia3CInfoSeriesDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete DIA 3C Info Series', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdspCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create BDSP', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdspRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View BDSP', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdspUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update BDSP', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdspDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete BDSP', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('elearningModuleContentCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create E-learning Module Content', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('elearningModuleContentRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View E-learning Module Content', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('elearningModuleContentUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update E-learning Module Content', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('elearningModuleContentDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete E-learning Module Content', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdsServiceProvidersOnboardingCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create BDS Service Providers Onboarding', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdsServiceProvidersOnboardingRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View BDS Service Providers Onboarding', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdsServiceProvidersOnboardingUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update BDS Service Providers Onboarding', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bdsServiceProvidersOnboardingDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete BDS Service Providers Onboarding', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bulkBroadcastCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Bulk Broadcast', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bulkBroadcastRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Bulk Broadcast', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bulkBroadcastUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Bulk Broadcast', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('bulkBroadcastDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Bulk Broadcast', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('discussionForumCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Discussion Forum', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('discussionForumRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Discussion Forum', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('discussionForumUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Discussion Forum', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('discussionForumDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Discussion Forum', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('latestDevelopmentsCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Latest Developments', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('latestDevelopmentsRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Latest Developments', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('latestDevelopmentsUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Latest Developments', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('latestDevelopmentsDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Latest Developments', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('popUpsCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Pop-Ups', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('popUpsRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Pop-Ups', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('popUpsUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Pop-Ups', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('popUpsDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Pop-Ups', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('actionPlanCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Action Plan', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('actionPlanRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Action Plan', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('actionPlanUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Action Plan', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('actionPlanDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Action Plan', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('surveyCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Survey', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('surveyRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'View Survey', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('surveyUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Survey', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('surveyDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Survey', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('industryAssociationDropDown', 'MANPOWER_AGENCY,BSE,GT_FIELD_TEAM,GT_PMU,SIDBI_SDE,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER,CLUSTER_EXPERT', 'IA Drop Down', SYSTIMESTAMP);
-- Action Plan Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('actionPlanCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Action Plan', SYSTIMESTAMP);

INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('actionPlanRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Read Action Plan', SYSTIMESTAMP);

INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('actionPlanUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Action Plan', SYSTIMESTAMP);

INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('actionPlanDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Action Plan', SYSTIMESTAMP);


-- Survey Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('surveyCreate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Create Survey', SYSTIMESTAMP);

INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('surveyRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Read Survey', SYSTIMESTAMP);

INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('surveyUpdate', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Update Survey', SYSTIMESTAMP);

INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at)
VALUES ('surveyDelete', 'GT_FIELD_TEAM,GT_PMU,SIDBI_HO_CHECKER', 'Delete Survey', SYSTIMESTAMP);

-- Grant Disbursement Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('grantDisbursementCreate', 'GT_FIELD_TEAM,GT_PMU', 'Create Grant Disbursement', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('grantDisbursementRead', 'GT_FIELD_TEAM,GT_PMU', 'View Grant Disbursement', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('grantDisbursementUpdate', 'GT_FIELD_TEAM,GT_PMU', 'Update Grant Disbursement', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('grantDisbursementDelete', 'GT_FIELD_TEAM,GT_PMU', 'Delete Grant Disbursement', SYSTIMESTAMP);


-- DIA Enhancement Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaEnhancementCreate', 'GT_FIELD_TEAM,GT_PMU', 'Create DIA Enhancement', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaEnhancementRead', 'GT_FIELD_TEAM,GT_PMU', 'View DIA Enhancement', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaEnhancementUpdate', 'GT_FIELD_TEAM,GT_PMU', 'Update DIA Enhancement', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaEnhancementDelete', 'GT_FIELD_TEAM,GT_PMU', 'Delete DIA Enhancement', SYSTIMESTAMP);


-- DIA Capacity Building Program Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCapacityBuildingProgramCreate', 'GT_FIELD_TEAM,GT_PMU', 'Create DIA Capacity Building Program', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCapacityBuildingProgramRead', 'GT_FIELD_TEAM,GT_PMU', 'View DIA Capacity Building Program', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCapacityBuildingProgramUpdate', 'GT_FIELD_TEAM,GT_PMU', 'Update DIA Capacity Building Program', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCapacityBuildingProgramDelete', 'GT_FIELD_TEAM,GT_PMU', 'Delete DIA Capacity Building Program', SYSTIMESTAMP);


-- DIA MSME Participant Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaMsmeParticipantCreate', 'GT_FIELD_TEAM,GT_PMU', 'Create DIA MSME Participant', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaMsmeParticipantRead', 'GT_FIELD_TEAM,GT_PMU', 'View DIA MSME Participant', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaMsmeParticipantUpdate', 'GT_FIELD_TEAM,GT_PMU', 'Update DIA MSME Participant', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaMsmeParticipantDelete', 'GT_FIELD_TEAM,GT_PMU', 'Delete DIA MSME Participant', SYSTIMESTAMP);


-- DIA Exposure Visit Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitCreate', 'GT_FIELD_TEAM,GT_PMU', 'Create DIA Exposure Visit', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitRead', 'GT_FIELD_TEAM,GT_PMU', 'View DIA Exposure Visit', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitUpdate', 'GT_FIELD_TEAM,GT_PMU', 'Update DIA Exposure Visit', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitDelete', 'GT_FIELD_TEAM,GT_PMU', 'Delete DIA Exposure Visit', SYSTIMESTAMP);


-- DIA Exposure Visit Participant Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitParticipantCreate', 'GT_FIELD_TEAM,GT_PMU', 'Create DIA Exposure Visit Participant', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitParticipantRead', 'GT_FIELD_TEAM,GT_PMU', 'View DIA Exposure Visit Participant', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitParticipantUpdate', 'GT_FIELD_TEAM,GT_PMU', 'Update DIA Exposure Visit Participant', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaExposureVisitParticipantDelete', 'GT_FIELD_TEAM,GT_PMU', 'Delete DIA Exposure Visit Participant', SYSTIMESTAMP);


-- DIA Credit Application Policies
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCreditApplicationCreate', 'GT_FIELD_TEAM,GT_PMU', 'Create DIA Credit Application', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCreditApplicationRead', 'GT_FIELD_TEAM,GT_PMU', 'View DIA Credit Application', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCreditApplicationUpdate', 'GT_FIELD_TEAM,GT_PMU', 'Update DIA Credit Application', SYSTIMESTAMP);
INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('diaCreditApplicationDelete', 'GT_FIELD_TEAM,GT_PMU', 'Delete DIA Credit Application', SYSTIMESTAMP);
